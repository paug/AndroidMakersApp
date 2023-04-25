package fr.paug.androidmakers.ui

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.WindowInfoTracker
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AndroidMakersApp
import fr.paug.androidmakers.ui.components.ShowRationalPermissionDialog
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.ui.util.extension.toAMUser
import fr.paug.androidmakers.util.BookmarksStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

val LocalActivity = staticCompositionLocalOf<MainActivity> { throw NotImplementedError() }

data class AMUser(
    private val firebaseUser: FirebaseUser
) {
  val uid: String
    get() = firebaseUser.uid
  val photoUrl: String?
    get() = firebaseUser.photoUrl?.toString()
}

class MainActivity : AppCompatActivity() {
  private val _user = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.toAMUser())

  private lateinit var windowInfoTracker: WindowInfoTracker

  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  @RequiresApi(Build.VERSION_CODES.M)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    windowInfoTracker = WindowInfoTracker.getOrCreate(this@MainActivity)

    val currentUser = _user.value
    if (currentUser != null) {
      lifecycleScope.launch {
        // fire & forget
        // This is racy but oh well...
        mergeBookmarks(currentUser.uid)
      }
    }

    WindowCompat.setDecorFitsSystemWindows(window, false)

    logFCMToken()

    setContent {
      val rememberedActivity = remember { this }

      val userState = _user.collectAsState()

      val context = LocalContext.current
      val permissionOpenDialog = remember { mutableStateOf(false) }
      val rationalPermissionOpenDialog = remember { mutableStateOf(false) }

      var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          mutableStateOf(
              ContextCompat.checkSelfPermission(
                  context,
                  POST_NOTIFICATIONS
              ) == PackageManager.PERMISSION_GRANTED
          )
        } else mutableStateOf(true)
      }

      val launcher = rememberLauncherForActivityResult(
          contract = ActivityResultContracts.RequestPermission(),
          onResult = { isGranted ->
            if (!isGranted) {
              if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                rationalPermissionOpenDialog.value = true
              } else {
                permissionOpenDialog.value = true
              }
            } else {
              hasNotificationPermission = isGranted
            }
          }
      )

      LaunchedEffect(true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          launcher.launch(POST_NOTIFICATIONS)
        }
      }

      CompositionLocalProvider(
          LocalActivity provides rememberedActivity,
      ) {
        AndroidMakersTheme {
          if (rationalPermissionOpenDialog.value) {
            ShowRationalPermissionDialog(
                packageName = packageName,
                openDialog = rationalPermissionOpenDialog
            ) {
              rationalPermissionOpenDialog.value = false
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(POST_NOTIFICATIONS)
              }
            }
          }

          val displayFeatures = calculateDisplayFeatures(this)
          val windowSizeClass = calculateWindowSizeClass(this)

          AndroidMakersApp(
              displayFeatures = displayFeatures,
              windowSizeClass = windowSizeClass,
              user = userState.value
          )
        }
      }
    }
  }

  private fun logFCMToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
      if (!task.isSuccessful) {
        Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
        return@OnCompleteListener
      }
      val token = task.result
      Log.d("MainActivity", token)
    })
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when (requestCode) {
      REQ_SIGN_IN -> {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
          val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
          val idToken = account.idToken
          when {
            idToken != null -> {
              // Got an ID token from Google. Use it to authenticate with Firebase.
              val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
              val auth = FirebaseAuth.getInstance()

              auth.signInWithCredential(firebaseCredential)
                  .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                      // Sign in success, update UI with the signed-in user's information
                      lifecycleScope.launch {
                        _user.value = auth.currentUser?.toAMUser()
                        mergeBookmarks(auth.currentUser!!.uid)
                      }
                    } else {
                      // If sign in fails, display a message to the user.
                      task.exception?.printStackTrace()
                      _user.value = null
                    }
                  }
            }
            else -> {
              _user.value = null
              // Shouldn't happen.
            }
          }
        } catch (e: ApiException) {
          e.printStackTrace()
        }
      }
    }
  }

  suspend fun mergeBookmarks(userId: String) {
    val bookmarks = AndroidMakersApplication.instance()
        .store
        .getBookmarks(userId)
        .firstOrNull()
        ?.getOrNull()
    if (bookmarks != null) {
      BookmarksStore.merge(bookmarks)
    }
  }

  fun signOut() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    FirebaseAuth.getInstance().signOut()
    googleSignInClient.signOut()
    googleSignInClient.revokeAccess()
    _user.value = null
  }

  fun signIn() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    activity.startActivityForResult(googleSignInClient.signInIntent, REQ_SIGN_IN)
  }

  companion object {
    const val REQ_SIGN_IN = 33
  }

}

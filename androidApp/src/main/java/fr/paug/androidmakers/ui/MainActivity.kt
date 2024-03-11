package fr.paug.androidmakers.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.store.firebase.toUser
import fr.androidmakers.domain.utils.UrlOpener
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.MainLayout
import fr.paug.androidmakers.ui.components.about.AboutActions
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.CustomTabUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

val LocalActivity = staticCompositionLocalOf<MainActivity> { throw NotImplementedError() }

class MainActivity : AppCompatActivity() {

  private val userRepository = AndroidMakersApplication.instance().userRepository

  private val _user = MutableStateFlow<User?>(null)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      _user.emit(userRepository.getUser())
    }

    val currentUser = _user.value
    if (currentUser != null) {
      lifecycleScope.launch {
        // fire & forget
        // This is racy but oh well...
        mergeBookmarks(currentUser.id)
      }
    }

    WindowCompat.setDecorFitsSystemWindows(window, false)
    enableEdgeToEdge()

    logFCMToken()

    setContent {
      val rememberedActivity = remember { this }

      val userState = _user.collectAsState()
      val darkTheme = isSystemInDarkTheme()

      CompositionLocalProvider(
          LocalActivity provides rememberedActivity,
      ) {
        AndroidMakersTheme {
          DisposableEffect(darkTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                ) { darkTheme },
                navigationBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                ) { darkTheme },
            )
            onDispose { }
          }

          MainLayout(
              aboutActions = AboutActions(
                  onFaqClick = ::onFaqClick,
                  onCodeOfConductClick = ::onCodeOfConductClick,
                  onXHashtagClick = ::onXHashtagClick,
                  onXLogoClick = ::onXLogoClick,
                  onYouTubeLogoClick = ::onYouTubeLogoClick,
                  onSponsorClick = ::onSponsorClick,
              ),
              user = userState.value,
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

  private fun onFaqClick() {
    AndroidMakersApplication.instance().openFaqUseCase()
  }

  private fun onCodeOfConductClick() {
    AndroidMakersApplication.instance().openCocUseCase()
  }

  private fun onXHashtagClick() {
    AndroidMakersApplication.instance().openXHashtagUseCase()
  }

  private fun onXLogoClick() {
    AndroidMakersApplication.instance().openXAccountUseCase()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when (requestCode) {
      REQ_SIGNIN -> {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
          val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
          val idToken = account.idToken
          when {
            idToken != null -> {
              // Got an ID token from Google. Use it to authenticate
              // with Firebase.
              // Got an ID token from Google. Use it to authenticate
              // with Firebase.
              val firebaseCredential = GoogleAuthProvider.credential(idToken, null)
              val auth = Firebase.auth

              CoroutineScope(Dispatchers.Default).launch {
                val result = auth.signInWithCredential(firebaseCredential)
                // Sign in success, update UI with the signed-in user's information
                lifecycleScope.launch {
                  _user.value = result.user?.toUser()
                  mergeBookmarks(auth.currentUser!!.uid)
                }
              }
            }

            else -> {}
          }
        } catch (e: ApiException) {
          e.printStackTrace()
          _user.value = null
        }
      }
    }
  }

  suspend fun mergeBookmarks(userId: String) {
    AndroidMakersApplication.instance().syncBookmarksUseCase(userId)
  }

  private fun onYouTubeLogoClick() {
    AndroidMakersApplication.instance().openYoutubeUseCase()
  }

  private fun onSponsorClick(url: String) {
    CustomTabUtil.openChromeTab(this, url)
  }

  fun signout() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    lifecycleScope.launch {
      Firebase.auth.signOut()
      googleSignInClient.signOut()
      googleSignInClient.revokeAccess()
      _user.emit(null)
    }
  }

  fun signin() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    activity.startActivityForResult(googleSignInClient.signInIntent, REQ_SIGNIN)
  }

  companion object {
    const val REQ_SIGNIN = 33
  }

}

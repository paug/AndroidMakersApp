package fr.paug.androidmakers.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.MainLayout
import fr.paug.androidmakers.ui.components.about.AboutActions
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.BookmarksStore
import fr.paug.androidmakers.util.CustomTabUtil
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

private fun FirebaseUser.toAMUser(): AMUser {
  return AMUser(this)
}

class MainActivity : AppCompatActivity() {
  private val _user = MutableStateFlow(FirebaseAuth.getInstance().currentUser?.toAMUser())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val currentUser = _user.value
    if (currentUser != null) {
      lifecycleScope.launch {
        // fire & forget
        // This is racy but oh well...
        mergeBookmarks(currentUser.uid)
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
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.droidcon.com/faqs/")))
  }

  private fun onCodeOfConductClick() {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.droidcon.com/code-of-conduct/")))
  }

  private fun onXHashtagClick() {
    var xIntent: Intent
    try {
      // get the X app if possible
      packageManager?.getPackageInfo("com.twitter.android", 0)
      xIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("twitter://search?query=%23" + getString(R.string.x_hashtag_for_query))
      )
      xIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
      // no X app, revert to browser
      xIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://x.com/search?q=%23" + getString(R.string.x_hashtag_for_query))
      )
    }

    startActivity(xIntent)
  }

  private fun onXLogoClick() {
    var xIntent: Intent
    try {
      // get the X app if possible. The package name is still "Twitter"
      packageManager?.getPackageInfo("com.twitter.android", 0)
      xIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("twitter://user?screen_name=" + getString(R.string.x_user_name))
      )
      xIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
      // no X app, revert to browser
      xIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://x.com/" + getString(R.string.x_user_name))
      )
    }

    startActivity(xIntent)
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

  private fun onYouTubeLogoClick() {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_channel))))
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

    FirebaseAuth.getInstance().signOut()
    googleSignInClient.signOut()
    googleSignInClient.revokeAccess()
    _user.value = null
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

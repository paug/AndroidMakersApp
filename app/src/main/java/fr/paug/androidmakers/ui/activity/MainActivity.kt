package fr.paug.androidmakers.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AboutActions
import fr.paug.androidmakers.ui.components.MainLayout
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.BookmarksStore
import fr.paug.androidmakers.util.CustomTabUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


val LocalActivity = staticCompositionLocalOf<MainActivity> { throw NotImplementedError() }

data class AMUser(
    val uid: String,
    val photoUrl: String?,
)

private fun FirebaseUser.toAMUser(): AMUser {
  return AMUser(this.uid, this.photoUrl?.toString())
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

    setContent {
      val rememberedActivity = remember { this }

      val userState = _user.collectAsState()

      CompositionLocalProvider(
          LocalActivity provides rememberedActivity,
      ) {
        AndroidMakersTheme {
          MainLayout(
              aboutActions = AboutActions(
                  onFaqClick = ::onFaqClick,
                  onCodeOfConductClick = ::onCodeOfConductClick,
                  onTwitterHashtagClick = ::onTwitterHashtagClick,
                  onTwitterLogoClick = ::onTwitterLogoClick,
                  onYouTubeLogoClick = ::onYouTubeLogoClick,
                  onSponsorClick = ::onSponsorClick,
              ),
              user = userState.value,
          )
        }
      }
    }
  }

  private fun onFaqClick() {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.fr/faq")))
  }

  private fun onCodeOfConductClick() {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.fr/coc")))
  }

  private fun onTwitterHashtagClick() {
    var twitterIntent: Intent
    try {
      // get the Twitter app if possible
      packageManager?.getPackageInfo("com.twitter.android", 0)
      twitterIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("twitter://search?query=%23" + getString(R.string.twitter_hashtag_for_query))
      )
      twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
      // no Twitter app, revert to browser
      twitterIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://twitter.com/search?q=%23" + getString(R.string.twitter_hashtag_for_query))
      )
    }

    startActivity(twitterIntent)
  }

  private fun onTwitterLogoClick() {
    var twitterIntent: Intent
    try {
      // get the Twitter app if possible
      packageManager?.getPackageInfo("com.twitter.android", 0)
      twitterIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("twitter://user?screen_name=" + getString(R.string.twitter_user_name))
      )
      twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
      // no Twitter app, revert to browser
      twitterIntent = Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://twitter.com/" + getString(R.string.twitter_user_name))
      )
    }

    startActivity(twitterIntent)
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
                        mergeBookmarks(auth.currentUser!!.uid)
                        _user.value = auth.currentUser?.toAMUser()
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
        ?.getOrNull()?: error("no bookmarks")
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

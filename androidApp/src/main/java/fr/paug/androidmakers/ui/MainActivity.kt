package fr.paug.androidmakers.ui

import android.content.Intent
import android.graphics.Color
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
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.store.firebase.toUser
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.MainLayout
import fr.paug.androidmakers.ui.components.about.AboutActions
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.CustomTabUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

val LocalActivity = staticCompositionLocalOf<MainActivity> { throw NotImplementedError() }

class MainActivity : AppCompatActivity() {
  private val viewModel: MainActivityViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)
    enableEdgeToEdge()

    logFCMToken()

    setContent {
      val rememberedActivity = remember { this }

      val userState = viewModel.user.collectAsState(null)
      val darkTheme = isSystemInDarkTheme()

      CompositionLocalProvider(
          LocalActivity provides rememberedActivity,
      ) {
        KoinContext {
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
    viewModel.openFaqUseCase()
  }

  private fun onCodeOfConductClick() {
    viewModel.openCocUseCase()
  }

  private fun onXHashtagClick() {
    viewModel.openXHashtagUseCase()
  }

  private fun onXLogoClick() {
    viewModel.openXAccountUseCase()
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
                  viewModel._user.value = result.user?.toUser()
                  viewModel.syncBookmarksUseCase(auth.currentUser!!.uid)
                }
              }
            }

            else -> {}
          }
        } catch (e: ApiException) {
          e.printStackTrace()
          viewModel._user.value = null
        }
      }
    }
  }

  private fun onYouTubeLogoClick() {
    viewModel.openYoutubeUseCase()
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
      viewModel._user.emit(null)
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

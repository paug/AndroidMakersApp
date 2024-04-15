package fr.paug.androidmakers

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
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.androidmakers.ui.LocalPlatformContext
import com.androidmakers.ui.MainLayout
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.common.navigation.UserData
import com.androidmakers.ui.theme.AndroidMakersTheme
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    logFCMToken()

    setContent {
      val rememberedActivity = remember { this }

      CompositionLocalProvider(
        LocalPlatformContext provides rememberedActivity,
      ) {
        KoinContext {
          AndroidMakersTheme {
            val darkTheme = isSystemInDarkTheme()
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
              versionName = BuildConfig.VERSION_NAME,
              versionCode = BuildConfig.VERSION_CODE.toString(),
              signinCallbacks = SigninCallbacks(
                signin = { signin() },
                signout = { signout() },
              )
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
              val firebaseCredential = GoogleAuthProvider.credential(idToken, null)
              val auth = Firebase.auth

              CoroutineScope(Dispatchers.Default).launch {
                val result = auth.signInWithCredential(firebaseCredential)
                // Sign in success, update UI with the signed-in user's information
                lifecycleScope.launch {
                  UserData().userRepository.setUser(result.user)
                  println("user id=${result.user?.uid}")
                  println("idToken=${result.user?.getIdToken(true)}")
                }
              }
            }

            else -> {}
          }
        } catch (e: ApiException) {
          e.printStackTrace()
          lifecycleScope.launch {
            UserData().userRepository.setUser(null)
          }
        }
      }
    }
  }

  fun signout() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken("985196411897-r7edbi9jgo3hfupekcmdrg66inonj0o5.apps.googleusercontent.com")
      .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    lifecycleScope.launch {
      Firebase.auth.signOut()
      googleSignInClient.signOut()
      googleSignInClient.revokeAccess()
      UserData().userRepository.setUser(null)
    }
  }

  fun signin() {
    val activity = this
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken("985196411897-r7edbi9jgo3hfupekcmdrg66inonj0o5.apps.googleusercontent.com")
      .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    activity.startActivityForResult(googleSignInClient.signInIntent, REQ_SIGNIN)
  }

  companion object {
    const val REQ_SIGNIN = 33
  }
}



package fr.paug.androidmakers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.lifecycleScope
import com.androidmakers.ui.MainLayout
import com.androidmakers.ui.common.SigninCallbacks
import fr.androidmakers.domain.model.ThemePreference
import fr.androidmakers.domain.repo.ThemeRepository
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.messaging.FirebaseMessaging
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.interactor.MergeBookmarksUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val credentialManager: CredentialManager by lazy(LazyThreadSafetyMode.NONE) {
    CredentialManager.create(this)
  }

  private val mergeBookmarksUseCase: MergeBookmarksUseCase by inject(mode = LazyThreadSafetyMode.NONE)

  private val notificationPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { granted ->
    Log.d(TAG, "POST_NOTIFICATIONS permission ${if (granted) "granted" else "denied"}")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)

    requestNotificationPermission()
    logFCMToken()

    val initialDeepLink: String? = if (savedInstanceState == null) intent.dataString else null

    setContent {
      val themeRepository: ThemeRepository = org.koin.compose.koinInject()
      val themePreference by themeRepository.themePreference.collectAsState(ThemePreference.System)
      val isDark = when (themePreference) {
        ThemePreference.System -> isSystemInDarkTheme()
        ThemePreference.Light -> false
        ThemePreference.Dark -> true
        ThemePreference.Neobrutalism -> false
      }

      DisposableEffect(isDark) {
        enableEdgeToEdge(
          statusBarStyle = if (isDark) {
            SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
          } else {
            SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
          },
          navigationBarStyle = if (isDark) {
            SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
          } else {
            SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
          },
        )
        onDispose {}
      }

      val deeplink: String? by produceState(initialDeepLink) {
        val listener = Consumer<Intent> { newIntent ->
          newIntent.dataString?.let {
            value = it
          }
        }
        addOnNewIntentListener(listener)
        awaitDispose { removeOnNewIntentListener(listener) }
      }

      MainLayout(
        versionName = BuildConfig.VERSION_NAME,
        versionCode = BuildConfig.VERSION_CODE.toString(),
        deeplink = deeplink,
        signinCallbacks = SigninCallbacks(
          signin = ::signIn,
          signout = ::signOut,
        )
      )
    }
  }

  private fun requestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED
      ) {
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
    }
  }

  @Suppress("TooGenericExceptionCaught")
  private fun logFCMToken() {
    lifecycleScope.launch {
      try {
        val token = FirebaseMessaging.getInstance().token.await()
        Log.d(TAG, "FCM registration token: $token")
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.w(TAG, "Fetching FCM registration token failed", e)
      }
    }
  }

  private fun signOut() {
    lifecycleScope.launch(NonCancellable) {
      Firebase.auth.signOut()
      credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
  }

  private fun signIn() {
    // See https://github.com/android/identity-samples/issues/86
    val googleIdOption = GetSignInWithGoogleOption.Builder(SERVER_CLIENT_ID)
      .build()
    val request: GetCredentialRequest = GetCredentialRequest.Builder()
      .addCredentialOption(googleIdOption)
      .build()

    lifecycleScope.launch {
      try {
        val response = credentialManager.getCredential(this@MainActivity, request)
        val credential = response.credential
        val isGoogleIdToken = credential is CustomCredential &&
          credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        if (isGoogleIdToken) {
          try {
            handleGoogleIdTokenCredential(GoogleIdTokenCredential.createFrom(credential.data))
          } catch (e: GoogleIdTokenParsingException) {
            Log.e(TAG, "Received an invalid google id token response", e)
          }
        } else {
          Log.e(TAG, "Unexpected type of credential")
        }
      } catch (e: NoCredentialException) {
        Log.d(TAG, "No credentials available", e)
      } catch (e: GetCredentialException) {
        Log.e(TAG, "Retrieving of credential failed", e)
      }
    }
  }

  private suspend fun handleGoogleIdTokenCredential(googleIdTokenCredential: GoogleIdTokenCredential) {
    // Got an ID token from Google. Use it to authenticate with Firebase.GoogleSignIn
    val firebaseCredential = GoogleAuthProvider.credential(googleIdTokenCredential.idToken, null)
    val result = Firebase.auth.signInWithCredential(firebaseCredential)
    // Sign in success, update UI with the signed-in user's information
    result.user?.uid?.let {
      mergeBookmarksUseCase(it)
    }

    Log.d(TAG, "user id=${result.user?.uid}")
    Log.d(TAG, "idToken=${result.user?.getIdToken(true)}")
  }

  companion object {
    private const val TAG = "MainActivity"

    /**
     * This is taken from the firebase google-services.json
     * It is the same value for both debug and release builds.
     * For some reason, trying to use the client ids from the google cloud console doesn't work 🤷‍♂️
     * See https://github.com/android/identity-samples/issues/53#issuecomment-2579235790
     */
    private const val SERVER_CLIENT_ID =
      "985196411897-r7edbi9jgo3hfupekcmdrg66inonj0o5.apps.googleusercontent.com"
  }
}

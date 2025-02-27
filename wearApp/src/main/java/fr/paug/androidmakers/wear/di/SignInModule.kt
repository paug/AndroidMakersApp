package fr.paug.androidmakers.wear.di

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.horologist.auth.data.googlesignin.GoogleSignInEventListener
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import fr.paug.androidmakers.wear.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val TAG = GoogleSignInEventListener::class.java.simpleName

val signInModule = module {
  single<GoogleSignInClient> {
    val context = androidContext()
    GoogleSignIn.getClient(
      context,
      GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .build()
    )
  }
  single<GoogleSignInEventListener> {
    object : GoogleSignInEventListener {
      override suspend fun onSignedIn(account: GoogleSignInAccount) {
        Log.d(TAG, "Google sign in success")
        try {
          val credential = GoogleAuthProvider.credential(account.idToken!!, null)
          Firebase.auth.signInWithCredential(credential)
        } catch (e: FirebaseAuthException) {
          Log.w(TAG, "Could not get Firebase auth credential from Google id token", e)
        }
      }
    }
  }
}

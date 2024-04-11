package fr.paug.androidmakers.wear.ui.signin

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.horologist.auth.data.googlesignin.GoogleSignInEventListener
import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.applicationContext
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val TAG = GoogleSignInViewModel::class.java.simpleName

class GoogleSignInViewModel(
    onSignInSuccess: () -> Unit,
    onSignInFailed: () -> Unit,
) : GoogleSignInViewModel(
    googleSignInClient = GoogleSignIn.getClient(
        applicationContext,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
            .build()
    ),
    googleSignInEventListener = object : GoogleSignInEventListener {
      override suspend fun onSignedIn(account: GoogleSignInAccount) {
        Log.d(TAG, "Google sign in success")
        val idToken = account.idToken
        try {
          val credential = GoogleAuthProvider.getCredential(idToken!!, null)
          Firebase.auth.signInWithCredential(credential).await()
          UserData().userRepository.setUser(Firebase.auth.currentUser?.toUser())
          onSignInSuccess()
        } catch (e: FirebaseAuthException) {
          Log.w(TAG, "Could not get Firebase auth credential from Google id token", e)
          UserData().userRepository.setUser(null)
          onSignInFailed()
        }
      }
    }
)

private suspend fun FirebaseUser.toUser(): User {
  return User(
    id = this.uid,
    photoUrl = this.photoUrl.toString(),
    idToken = this.getIdToken(true).await().token
  )
}

class UserData: KoinComponent {
  val userRepository: UserRepository by inject()
}

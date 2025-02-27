package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseUserRepository : UserRepository {
  // Use Firebase.auth as single source of truth

  override val currentUser: User?
    get() = Firebase.auth.currentUser?.toUser()

  override val user: Flow<User?>
    get() = Firebase.auth.authStateChanged.map { it?.toUser() }

  override suspend fun getIdToken(): String? {
    return try {
      Firebase.auth.currentUser?.getIdToken(false)
    } catch (e: CancellationException) {
      throw e
    } catch (ignore: Exception) {
      // See https://github.com/firebase/firebase-android-sdk/issues/5328#issuecomment-1719386926
      null
    }
  }
}

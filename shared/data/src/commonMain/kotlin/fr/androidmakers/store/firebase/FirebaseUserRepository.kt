package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseUserRepository : UserRepository {
  private var firebaseUser = MutableStateFlow<FirebaseUser?>(null)
  private val _user = MutableStateFlow<User?>(null)
  override val user = _user.asStateFlow()

  init {
    try {
      setUser(Firebase.auth.currentUser)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override suspend fun getIdToken(): String? {
    return try {
      firebaseUser.value?.getIdToken(false)
    } catch (ignore: Exception) {
      // See https://github.com/firebase/firebase-android-sdk/issues/5328#issuecomment-1719386926
      null
    }
  }

  override fun setUser(user: Any?) {
    check(user is FirebaseUser?) {
      "Expected FirebaseUser, got '$user'"
    }
    firebaseUser.value = user
    _user.value = user?.toUser()
  }
}

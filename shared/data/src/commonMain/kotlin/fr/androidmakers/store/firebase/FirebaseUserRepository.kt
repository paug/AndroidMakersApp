package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class FirebaseUserRepository : UserRepository {
  private var currentUser = atomic<FirebaseUser?>(null)
  override val user = MutableStateFlow<User?>(null)

  init {
    GlobalScope.launch {
      try {
        setUser(Firebase.auth.currentUser)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  override fun getUser(): User? {
    return user.value
  }

  override suspend fun getIdToken(): String? {
    return try {
      currentUser.value?.getIdToken(false)
    } catch (e: Exception) {
      // See https://github.com/firebase/firebase-android-sdk/issues/5328#issuecomment-1719386926
      null
    }
  }

  override suspend fun setUser(user: Any?) {
    check(user is FirebaseUser?) {
      "Expected FirebaseUser, got '$user'"
    }
    currentUser.value = user
    this.user.emit(user?.toUser())
  }
}

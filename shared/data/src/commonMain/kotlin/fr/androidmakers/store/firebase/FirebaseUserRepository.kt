package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class FirebaseUserRepository : UserRepository {
  override val user = MutableStateFlow<User?>(null)

  init {
    GlobalScope.launch {
      try {
        setUser(Firebase.auth.currentUser?.toUser())
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  override fun getUser(): User? {
    return user.value
  }

  override suspend fun setUser(user: User?) {
    this.user.emit(user)
  }
}

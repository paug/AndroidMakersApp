package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
  val user: StateFlow<User?>
  suspend fun getIdToken(): String?
  fun setUser(user: Any?)
}

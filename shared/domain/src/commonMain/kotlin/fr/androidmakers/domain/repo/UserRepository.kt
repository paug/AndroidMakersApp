package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
  val user: StateFlow<User?>
  fun getUser(): User?
  suspend fun setUser(user: User?)
}

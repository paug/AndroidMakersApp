package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  val currentUser: User?
  val user: Flow<User?>
  suspend fun getIdToken(): String?
}

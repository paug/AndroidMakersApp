package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeUserRepository : UserRepository {
    override var currentUser: User? = User(id = "test-user", photoUrl = null)
    override val user: Flow<User?> = MutableStateFlow(currentUser)
    override suspend fun getIdToken(): String? = "fake-token"
  override suspend fun getInstallationId(): String {
    return "foo"
  }
}

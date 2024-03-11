package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository

class FirebaseUserRepository : UserRepository {
  override suspend fun getUser(): User? {
    return Firebase.auth.currentUser?.toUser()
  }
}

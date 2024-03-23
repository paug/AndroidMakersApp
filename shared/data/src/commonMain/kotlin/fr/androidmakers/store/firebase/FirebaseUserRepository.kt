package fr.androidmakers.store.firebase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository

class FirebaseUserRepository : UserRepository {
  override fun getUser(): User? {
    return try {
      Firebase.auth.currentUser?.toUser()
  } catch (e: Exception) {
    null
    }
  }
}

package fr.androidmakers.store.firebase

import dev.gitlive.firebase.auth.FirebaseUser
import fr.androidmakers.domain.model.User

fun FirebaseUser.toUser(): User {
  return User(uid, photoURL)
}

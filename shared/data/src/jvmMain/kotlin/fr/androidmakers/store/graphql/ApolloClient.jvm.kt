package fr.androidmakers.store.graphql

import fr.androidmakers.domain.repo.UserRepository

actual suspend fun getIdToken(userRepository: UserRepository): String? {
  // We do not support signin on Desktop
  return null
}

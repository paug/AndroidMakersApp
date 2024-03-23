package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.User

interface UserRepository {
  fun getUser(): User?
}

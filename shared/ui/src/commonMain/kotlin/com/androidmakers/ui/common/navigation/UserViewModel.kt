package com.androidmakers.ui.common.navigation

import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.repo.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserData: KoinComponent {
  val userRepository: UserRepository by inject()
  val syncBookmarksUseCase: SyncBookmarksUseCase by inject()
}



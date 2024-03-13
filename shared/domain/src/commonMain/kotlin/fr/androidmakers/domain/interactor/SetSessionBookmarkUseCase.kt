package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.UserRepository

class SetSessionBookmarkUseCase(
    private val userRepository: UserRepository,
    private val sessionsRepository: SessionsRepository,
    private val bookmarksRepository: BookmarksRepository
) {
  suspend operator fun invoke(sessionId: String, isBookmark: Boolean) {
    userRepository.getUser()?.id?.let { token ->
       sessionsRepository.setBookmark(token, sessionId, isBookmark)
    }

    bookmarksRepository.setBookmarked(sessionId, isBookmark)
  }
}

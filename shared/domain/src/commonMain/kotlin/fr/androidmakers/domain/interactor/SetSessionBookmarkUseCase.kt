package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.MessagingRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.UserRepository

class SetSessionBookmarkUseCase(
  private val userRepository: UserRepository,
  private val sessionsRepository: SessionsRepository,
  private val bookmarksRepository: BookmarksRepository,
  private val messagingRepository: MessagingRepository,
) {
  suspend operator fun invoke(sessionId: String, isBookmark: Boolean) {
    bookmarksRepository.setBookmarked(sessionId, isBookmark)
    userRepository.user.value?.id?.let { token ->
      sessionsRepository.setBookmark(token, sessionId, isBookmark)
    }
    messagingRepository.sendSyncBookmarksMessage()
  }
}

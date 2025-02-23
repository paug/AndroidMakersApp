package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import kotlinx.coroutines.flow.firstOrNull

class MergeBookmarksUseCase(
    private val bookmarksRepository: BookmarksRepository,
    private val sessionsRepository: SessionsRepository
) {
  suspend operator fun invoke(userId: String) {
    sessionsRepository
      .getBookmarks(userId)
      .firstOrNull()
      ?.onSuccess { bookmarks ->
        bookmarksRepository.merge(bookmarks)
      }
  }
}

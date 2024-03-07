package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import kotlinx.coroutines.flow.firstOrNull

class SyncBookmarksUseCase(
    private val bookmarksRepository: BookmarksRepository,
    private val sessionsRepository: SessionsRepository
) {
  suspend operator fun invoke(userId: String) {
    val bookmarks = sessionsRepository
        .getBookmarks(userId)
        .firstOrNull()
        ?.getOrNull()
    bookmarks?.let {
      bookmarksRepository.merge(it)
    }
  }
}

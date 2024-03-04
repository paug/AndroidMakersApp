package fr.androidmakers.domain.repo

import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
  suspend fun setBookmarked(sessionId: String, bookmarked: Boolean)
  fun isBookmarked(id: String): Boolean

  fun subscribe(id: String): Flow<Boolean>

  suspend fun merge(bookmarks: Set<String>)
}

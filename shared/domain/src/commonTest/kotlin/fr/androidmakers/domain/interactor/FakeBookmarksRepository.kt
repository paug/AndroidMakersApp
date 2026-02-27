package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeBookmarksRepository : BookmarksRepository {
    private val bookmarks = MutableStateFlow<Set<String>>(emptySet())
    var lastMergedBookmarks: Set<String>? = null

    override suspend fun setBookmarked(sessionId: String, bookmarked: Boolean) {
        bookmarks.value = if (bookmarked) {
            bookmarks.value + sessionId
        } else {
            bookmarks.value - sessionId
        }
    }

    override fun isBookmarked(id: String): Flow<Boolean> = bookmarks.map { id in it }

    override suspend fun merge(bookmarks: Set<String>) {
        lastMergedBookmarks = bookmarks
        this.bookmarks.value = this.bookmarks.value + bookmarks
    }

    override suspend fun setBookmarks(bookmarks: Set<String>) {
        this.bookmarks.value = bookmarks
    }

    override val favoriteSessions: Flow<Set<String>> = bookmarks
}

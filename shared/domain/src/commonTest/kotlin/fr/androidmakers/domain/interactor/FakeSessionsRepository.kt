package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.repo.SessionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSessionsRepository : SessionsRepository {
    val sessionsFlow = MutableStateFlow<Result<List<Session>>>(Result.success(emptyList()))
    val sessionFlow = MutableStateFlow<Result<Session>>(Result.failure(NoSuchElementException()))
    val bookmarksFlow = MutableStateFlow<Result<Set<String>>>(Result.success(emptySet()))
    var lastBookmarkCall: Triple<String, String, Boolean>? = null

    override fun getSession(id: String): Flow<Result<Session>> = sessionFlow
    override fun getSessions(refresh: Boolean): Flow<Result<List<Session>>> = sessionsFlow
    override fun getBookmarks(userId: String): Flow<Result<Set<String>>> = bookmarksFlow

    override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
        lastBookmarkCall = Triple(userId, sessionId, value)
    }
}

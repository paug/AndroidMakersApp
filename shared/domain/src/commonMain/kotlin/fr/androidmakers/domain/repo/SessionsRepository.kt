package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionsRepository {
  fun getSession(id: String): Flow<Result<Session>>
  fun getSessions(): Flow<Result<List<Session>>>

  fun getBookmarks(userId: String): Flow<Result<Set<String>>>
  suspend fun setBookmark(userId: String, sessionId: String, value: Boolean)
}

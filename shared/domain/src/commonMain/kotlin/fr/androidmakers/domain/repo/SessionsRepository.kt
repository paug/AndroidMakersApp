package fr.androidmakers.domain.repo

import at.asitplus.KmmResult
import fr.androidmakers.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionsRepository {
  fun getSession(id: String): Flow<KmmResult<Session>>

  fun getSessions(): Flow<KmmResult<List<Session>>>

  fun getBookmarks(userId: String): Flow<KmmResult<Set<String>>>

  suspend fun setBookmark(userId: String, sessionId: String, value: Boolean)
}

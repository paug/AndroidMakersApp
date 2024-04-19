package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.repo.SessionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionsGraphQLRepository(private val apolloClient: ApolloClient) : SessionsRepository {
  override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
    val mutation = if (value) {
      AddBookmarkMutation(sessionId)
    } else {
      RemoveBookmarkMutation(sessionId)
    }
    val response = apolloClient.mutation(mutation).execute()
    if (response.exception != null) {
      response.exception!!.printStackTrace()
    }
  }

  override fun getSession(id: String): Flow<Result<Session>> {
    return apolloClient.query(GetSessionQuery(id))
      .cacheAndNetwork()
      .map { it.map { it.session.sessionDetails.toSession() } }
  }

  override fun getBookmarks(uid: String): Flow<Result<Set<String>>> {
    return apolloClient.query(BookmarksQuery())
      .fetchPolicy(FetchPolicy.NetworkOnly)
      .toFlow()
      .map {
        it.dataAssertNoErrors.bookmarkConnection.nodes.map { it.id }.toSet()
      }
      .toResultFlow()
  }

  override fun getSessions(): Flow<Result<List<Session>>> {
    return apolloClient.query(GetSessionsQuery())
      .cacheAndNetwork()
      .map { it.map { it.sessions.nodes.map { it.sessionDetails.toSession() } } }
  }
}

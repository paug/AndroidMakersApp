package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
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

  override fun watchSessions(): Flow<List<Session>> {
    return apolloClient.query(GetSessionsQuery(after = Optional.present(null)))
      .fetchPolicy(FetchPolicy.CacheAndNetwork)
      .watch()
      .map { it.data?.sessions?.nodes?.map { it.sessionDetails.toSession() }.orEmpty() }
  }

  override suspend fun fetchNextSessionsPage() {
    val cacheResponse = apolloClient.query(GetSessionsQuery())
      .fetchPolicy(FetchPolicy.CacheOnly)
      .execute()
    val endCursor = cacheResponse.data?.sessions?.pageInfo?.endCursor
    if (endCursor != null) {
      apolloClient.query(GetSessionsQuery(Optional.present(endCursor)))
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .execute()
    }
  }
}

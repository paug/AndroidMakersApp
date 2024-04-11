package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.optimisticUpdates
import com.apollographql.apollo3.cache.normalized.refetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.store.graphql.type.buildBookmarkConnection
import fr.androidmakers.store.graphql.type.buildSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionsGraphQLRepository(private val apolloClient: ApolloClient) : SessionsRepository {

  suspend fun addBookmark(uid: String?, sessionId: String): Boolean {
    return modifyBookmarks(uid, AddBookmarkMutation(sessionId)) { sessionIds ->
      AddBookmarkMutation.Data {
        addBookmark = buildBookmarkConnection {
          nodes = (sessionIds + sessionId).map {
            buildSession {
              this.id = it
            }
          }
        }
      }
    }
  }

  suspend fun removeBookmark(uid: String?, sessionId: String): Boolean {
    return modifyBookmarks(uid, RemoveBookmarkMutation(sessionId)) { sessionIds ->
      RemoveBookmarkMutation.Data {
        removeBookmark = buildBookmarkConnection {
          nodes = (sessionIds - sessionId).map {
              buildSession {
                this.id = it
              }
            }
        }
      }
    }
  }

  override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
    try {
      if (value) {
        addBookmark(userId, sessionId)
      } else {
        removeBookmark(userId, sessionId)
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun getSession(id: String): Flow<Result<Session>> {
    return apolloClient.query(GetSessionQuery(id))
      .fetchPolicy(FetchPolicy.CacheAndNetwork)
      .watch()
      .ignoreCacheMisses()
      .map {
        it.dataAssertNoErrors.session.sessionDetails.toSession()
      }
      .toResultFlow()
  }

  override fun getBookmarks(uid: String): Flow<Result<Set<String>>> {
    return apolloClient.query(BookmarksQuery())
      .fetchPolicy(FetchPolicy.NetworkOnly)
      .refetchPolicy(FetchPolicy.CacheOnly)
      .watch().map {
        it.data!!.bookmarkConnection!!.nodes.map { it.id }.toSet()
      }.toResultFlow()
  }

  private suspend fun <D : Mutation.Data> modifyBookmarks(
    uid: String?,
    mutation: Mutation<D>,
    data: (sessionIds: List<String>) -> D
  ): Boolean {
    val optimisticData = try {
      val bookmarks = apolloClient.apolloStore.readOperation(BookmarksQuery()).bookmarkConnection
      data(bookmarks!!.nodes.map { it.id })
    } catch (e: Exception) {
      null
    }
    val response = apolloClient.mutation(mutation)
      .apply {
        if (optimisticData != null) {
          optimisticUpdates(optimisticData)
        }
      }
      .execute()

    return response.data != null
  }

  override fun getSessions(): Flow<Result<List<Session>>> {
    return apolloClient.query(GetSessionsQuery())
      .fetchPolicy(FetchPolicy.CacheAndNetwork)
      .watch()
      .ignoreCacheMisses()
      .map {
        it.dataAssertNoErrors.sessions.nodes.map { it.sessionDetails.toSession() }
      }
      .toResultFlow()
  }

}

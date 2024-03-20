package fr.androidmakers.store.graphql

import at.asitplus.KmmResult
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
import fr.androidmakers.store.graphql.type.buildBookmarks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionsGraphQLRepository(private val apolloClient: ApolloClient): SessionsRepository {

  suspend fun addBookmark(uid: String?, sessionId: String): Boolean {
    return modifyBookmarks(uid, AddBookmarkMutation(sessionId)) { sessionIds, id ->
      AddBookmarkMutation.Data {
        addBookmark = buildBookmarks {
          this.id = id
          this.sessionIds = sessionIds + sessionId
        }
      }
    }
  }

  suspend fun removeBookmark(uid: String?, sessionId: String): Boolean {
    return modifyBookmarks(uid, RemoveBookmarkMutation(sessionId)) { sessionIds, id ->
      RemoveBookmarkMutation.Data {
        removeBookmark = buildBookmarks {
          this.id = id
          this.sessionIds = sessionIds - sessionId
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

  override fun getSession(id: String): Flow<KmmResult<Session>> {
    return apolloClient.query(GetSessionQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch()
        .ignoreCacheMisses()
        .map {
          it.dataAssertNoErrors.session.sessionDetails.toSession()
        }
        .toResultFlow()
  }

  override fun getBookmarks(uid: String): Flow<KmmResult<Set<String>>> {
    return apolloClient.query(BookmarksQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .refetchPolicy(FetchPolicy.CacheOnly)
        .watch().map {
          it.data!!.bookmarks!!.sessionIds.toSet()
        }.toResultFlow()
  }

  private suspend fun <D : Mutation.Data> modifyBookmarks(
      uid: String?,
      mutation: Mutation<D>,
      data: (sessionIds: List<String>, id: String) -> D
  ): Boolean {
    val optimisticData = try {
      val bookmarks = apolloClient.apolloStore.readOperation(BookmarksQuery()).bookmarks
      data(bookmarks!!.sessionIds, bookmarks.id)
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

  override fun getSessions(): Flow<KmmResult<List<Session>>> {
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

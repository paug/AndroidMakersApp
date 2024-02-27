package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.optimisticUpdates
import com.apollographql.apollo3.cache.normalized.refetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.store.graphql.type.buildBookmarks
import fr.androidmakers.domain.model.Logo
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GraphQLStore(
    val apolloClient: ApolloClient
) : PartnersRepository, RoomsRepository, SessionsRepository, SpeakersRepository, VenueRepository {
  override fun getVenue(id: String): Flow<Result<Venue>> {
    return apolloClient.query(GetVenueQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.venue.toVenue()
        }
        .toResultFlow()
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return apolloClient.query(GetSpeakersQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.speakers.map { it.speakerDetails }.singleOrNull { it.id == id }?.toSpeaker()
              ?: error("no speaker")
        }
        .toResultFlow()
  }

  override fun getRoom(id: String): Flow<Result<Room>> {
    return getRooms().map {
      it.map { it.singleOrNull { it.id == id } ?: error("Not Room") }
    }
  }

  override fun getRooms(): Flow<Result<List<Room>>> {
    return apolloClient.query(GetRoomsQuery())
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .watch()
        .map {
          it.dataAssertNoErrors.rooms.map { it.roomDetails.toRoom() }
        }
        .toResultFlow()
  }

  override fun getBookmarks(uid: String): Flow<Result<Set<String>>> {
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

  override fun getSession(id: String): Flow<Result<Session>> {
    return apolloClient.query(GetSessionQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.session.sessionDetails.toSession()
        }
        .toResultFlow()
  }

  override fun getPartners(): Flow<Result<List<Partner>>> {
    return apolloClient.query(GetPartnerGroupsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.partnerGroups.map {
            Partner(
                title = it.title,
                logos = it.partners.map {
                  Logo(
                      logoUrl = it.logoUrl,
                      name = it.name,
                      url = it.url
                  )
                }
            )
          }
        }
        .toResultFlow()
  }


  override fun getSessions(): Flow<Result<List<Session>>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch()
        .map {
          it.dataAssertNoErrors.sessions.nodes.map { it.sessionDetails.toSession() }
        }
        .toResultFlow()
  }

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    return apolloClient.query(GetSpeakersQuery()).watch().map {
      it.dataAssertNoErrors.speakers.map { it.speakerDetails.toSpeaker() }
    }.toResultFlow()
  }

  private fun <T> Flow<T>.toResultFlow(): Flow<Result<T>> = this.map {
    Result.success(it)
  }.catch {
    emit(Result.failure<T>(it))
  }

}

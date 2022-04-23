package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.model.*
import kotlinx.coroutines.flow.*

class GraphQLStore(val apolloClient: ApolloClient) : AndroidMakersStore {
  override fun getVenue(id: String): Flow<Result<Venue>> {
    return apolloClient.query(GetVenueQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true).map {
          it.dataAssertNoErrors.venue.toVenue()
        }
        .toResultFlow()
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return apolloClient.query(GetSpeakersQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true).map {
          it.dataAssertNoErrors.speakers.map { it.speakerDetails }.single { it.id == id }.toSpeaker()
        }
        .toResultFlow()
  }

  override fun getRoom(id: String): Flow<Result<Room>> {
    return getRooms().map {
      it.map { it.single { it.id == id } }
    }
  }

  override fun getRooms(): Flow<Result<List<Room>>> {
    return apolloClient.query(GetRoomsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true)
        .map {
          it.dataAssertNoErrors.rooms.map { it.roomDetails.toRoom() }
        }
        .toResultFlow()
  }

  override fun getWifiInfo(): Flow<Result<WifiInfo?>> {
    return flowOf(Result.success(null))
  }

  override fun getBookmarks(userId: String): Flow<Result<Set<String>>> {
    return flowOf(Result.success(emptySet()))
  }

  override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
    TODO("Not yet implemented")
  }

  override fun getSession(id: String): Flow<Result<Session>> {
    return apolloClient.query(GetSessionQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true).map {
          it.dataAssertNoErrors.session.sessionDetails.toSession()
        }
        .toResultFlow()
  }

  override fun getPartners(): Flow<Result<List<Partner>>> {
    return apolloClient.query(GetPartnerGroupsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true).map {
          it.dataAssertNoErrors.partnerGroups.map {
            Partner(
                it.order,
                it.title,
                it.partners.map {
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

  override fun getScheduleSlots(): Flow<Result<List<ScheduleSlot>>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true)
        .map {
          it.dataAssertNoErrors.sessions.map { it.sessionDetails.toSlot() }
        }
        .toResultFlow()
  }


  override fun getSessions(): Flow<Result<List<Session>>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch(fetchThrows = true)
        .map {
          it.dataAssertNoErrors.sessions.map { it.sessionDetails.toSession() }
        }
        .toResultFlow()
  }

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    return apolloClient.query(GetSpeakersQuery()).watch(fetchThrows = true).map {
      it.dataAssertNoErrors.speakers.map { it.speakerDetails.toSpeaker() }
    }.toResultFlow()
  }

  private fun <T> Flow<T>.toResultFlow(): Flow<Result<T>> = this.onEach {
    //println("GraphQL item $it")
  }.map {
    Result.success(it)
  }.catch {
    println("GraphQL error ${it.message}")
    it.printStackTrace()
    Result.failure<T>(it)
  }
}

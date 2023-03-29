package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.model.Logo
import fr.androidmakers.store.model.Partner
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.androidmakers.store.model.Venue
import fr.androidmakers.store.model.WifiInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GraphQLStore(
    val apolloClient: ApolloClient
) : AndroidMakersStore {
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
          it.dataAssertNoErrors.speakers.map { it.speakerDetails }.single { it.id == id }
              .toSpeaker()
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
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .watch()
        .map {
          it.dataAssertNoErrors.rooms.map { it.roomDetails.toRoom() }
        }
        .toResultFlow()
  }

  override fun getWifiInfo(): Flow<Result<WifiInfo?>> {
    TODO("Not yet implemented")
  }

  override fun getBookmarks(userId: String): Flow<Result<Set<String>>> {
    TODO("Not yet implemented")
  }

  override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
    TODO("Not yet implemented")
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

  override fun getScheduleSlots(): Flow<Result<List<ScheduleSlot>>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch()
        .map {
          it.dataAssertNoErrors.sessions.nodes.map { it.sessionDetails.toSlot() }
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
    Result.failure<T>(it)
  }

}

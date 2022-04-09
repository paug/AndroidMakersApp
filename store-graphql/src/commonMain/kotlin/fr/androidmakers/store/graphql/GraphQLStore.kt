package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.store.model.*
import fr.androidmakers.store.*
import kotlinx.coroutines.flow.*

class GraphQLStore(val apolloClient: ApolloClient) : AndroidMakersStore {
  override fun getVenue(id: String): Flow<Venue> {
    return flowOf(Venue())
  }

  override fun getSpeaker(id: String): Flow<Speaker> {
    return apolloClient.query(GetSpeakersQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .watch().map {
      it.dataAssertNoErrors.speakers.map { it.speakerDetails }.single { it.id == id }.toSpeaker()
    }
  }

  override fun getRoom(id: String): Flow<Room> {
    return getRooms().map { it.single { it.id == id } }
  }

  override fun getRooms(): Flow<List<Room>> {
    return apolloClient.query(GetRoomsQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .watch()
        .map {
      it.dataAssertNoErrors.rooms.map { it.roomDetails.toRoom() }
    }
  }

  override fun getSession(id: String): Flow<Session> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .watch().map {
          it.dataAssertNoErrors.sessions.map { it.sessionDetails.toSession() }
        }.map {
          it.single { it.id == id }
        }
  }

  override fun getPartners(): Flow<List<Partner>> {
    return flowOf(
        emptyList()
    )
  }

  override fun getScheduleSlots(): Flow<List<ScheduleSlot>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .watch()
        .map {
          it.dataAssertNoErrors.sessions.map { it.sessionDetails.toSlot() }
        }
  }


  override fun getSessions(): Flow<List<Session>> {
    return apolloClient.query(GetSessionsQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .watch(
            fetchThrows = true
        ).map {
          it.dataAssertNoErrors.sessions.map { it.sessionDetails.toSession() }
        }
  }

  override fun getSpeakers(): Flow<List<Speaker>> {
    return apolloClient.query(GetSpeakersQuery()).watch().map {
      it.dataAssertNoErrors.speakers.map { it.speakerDetails.toSpeaker() }
    }
  }
}

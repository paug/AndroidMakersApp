package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomsGraphQLRepository(private val apolloClient: ApolloClient) : RoomsRepository {
  override fun getRoom(id: String): Flow<Result<Room>> {
    return apolloClient.query(GetRoomsQuery())
      .cacheAndNetwork()
      .map { dataResult ->
        dataResult.mapCatching { data ->
          data.rooms.firstOrNull { it.roomDetails.id == id }?.roomDetails?.toRoom()
            ?: error("Room not found")
        }
      }
  }

  override fun getRooms(refresh: Boolean): Flow<Result<List<Room>>> {
    return apolloClient.query(GetRoomsQuery())
      .cacheAndNetwork(refresh)
      .map { dataResult ->
        dataResult.map { data ->
          data.rooms.map { it.roomDetails.toRoom() }
        }
      }
  }
}

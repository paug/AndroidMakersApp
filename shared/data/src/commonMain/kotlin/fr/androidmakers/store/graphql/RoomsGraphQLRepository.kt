package fr.androidmakers.store.graphql

import at.asitplus.KmmResult
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomsGraphQLRepository(private val apolloClient: ApolloClient): RoomsRepository {
  override fun getRoom(id: String): Flow<KmmResult<Room>> {
    return getRooms().map {
      it.map { it.singleOrNull { it.id == id } ?: error("Not Room") }
    }
  }

  override fun getRooms(): Flow<KmmResult<List<Room>>> {
    return apolloClient.query(GetRoomsQuery())
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .watch()
        .map {
          it.dataAssertNoErrors.rooms.map { it.roomDetails.toRoom() }
        }
        .toResultFlow()
  }
}

package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SpeakersGraphQLRepository(private val apolloClient: ApolloClient) : SpeakersRepository {

  override fun getSpeakers(refresh: Boolean): Flow<Result<List<Speaker>>> {
    return apolloClient.query(GetSpeakersQuery())
      .cacheAndNetwork(refresh)
      .map { dataResult ->
        dataResult.map { data ->
          data.speakers.map {
            it.speakerDetails.toSpeaker()
          }
        }
      }
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return apolloClient.query(GetSpeakersQuery())
      .cacheAndNetwork()
      .map { dataResult ->
        dataResult.mapCatching { data ->
          data.speakers.firstOrNull { it.speakerDetails.id == id }?.speakerDetails?.toSpeaker()
            ?: error("Speaker with id $id not found")
        }
      }
  }

  override fun getSpeakers(ids: List<String>): Flow<Result<List<Speaker>>> {
    if (ids.isEmpty()) {
      return flowOf(Result.success(emptyList()))
    }
    return apolloClient.query(GetSpeakersQuery())
      .cacheAndNetwork(false)
      .map { dataResult ->
        dataResult.mapCatching { data ->
          val speakersDataById = data.speakers.associateBy { it.speakerDetails.id }
          ids.map { id ->
            val speakerData = speakersDataById[id] ?: error("Speaker with id $id not found")
            speakerData.speakerDetails.toSpeaker()
          }
        }
      }
  }
}

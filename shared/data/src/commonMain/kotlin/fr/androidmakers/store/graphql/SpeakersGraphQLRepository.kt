package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
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
    return getSpeakers(false).map { speakersResult ->
      speakersResult.mapCatching { speakers ->
        speakers.firstOrNull { it.id == id } ?: error("Speaker not found")
      }
    }
  }
}

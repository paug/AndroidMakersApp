package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import com.apollographql.apollo3.exception.DefaultApolloException
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpeakersGraphQLRepository(private val apolloClient: ApolloClient) : SpeakersRepository {

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    return apolloClient.query(GetSpeakersQuery())
      .cacheAndNetwork()
      .map { it.map { it.speakers.map { it.speakerDetails.toSpeaker() } } }
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return apolloClient.query(GetSpeakersQuery())
      .cacheAndNetwork()
      .map {
        if (it.isSuccess) {
          val speaker = it.getOrThrow().speakers.map { it.speakerDetails }.singleOrNull { it.id == id }?.toSpeaker()
          if (speaker != null) {
            Result.success(speaker)
          } else {
            Result.failure(DefaultApolloException("Something wrong happend"))
          }
        } else {
          Result.failure(it.exceptionOrNull()!!)
        }
      }
  }
}

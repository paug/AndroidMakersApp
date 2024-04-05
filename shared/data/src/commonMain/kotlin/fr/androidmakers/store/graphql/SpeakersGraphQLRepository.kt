package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpeakersGraphQLRepository(private val apolloClient: ApolloClient) : SpeakersRepository {

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    return apolloClient.query(GetSpeakersQuery())
      .fetchPolicy(FetchPolicy.CacheAndNetwork)
      .watch()
      .ignoreCacheMisses()
      .map {
        it.dataAssertNoErrors.speakers.map { it.speakerDetails.toSpeaker() }
      }.toResultFlow()
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return apolloClient.query(GetSpeakersQuery())
      .fetchPolicy(FetchPolicy.CacheAndNetwork)
      .watch()
      .ignoreCacheMisses()
      .map {
        it.dataAssertNoErrors.speakers.map { it.speakerDetails }.singleOrNull { it.id == id }
          ?.toSpeaker()
          ?: error("no speaker")
      }
      .toResultFlow()
  }
}

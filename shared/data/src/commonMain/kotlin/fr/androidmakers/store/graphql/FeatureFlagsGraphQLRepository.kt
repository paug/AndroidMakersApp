package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.GlobalBuilder
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.fetchPolicy
import fr.androidmakers.domain.model.FeatureFlags
import fr.androidmakers.domain.repo.FeatureFlagsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FeatureFlagsGraphQLRepository(private val apolloClient: ApolloClient): FeatureFlagsRepository {
  private fun GetFeatureFlagsQuery.FeatureFlags.toFeatureFlags() = FeatureFlags(feed, venue)

  override suspend fun flags(): FeatureFlags {
    val response = apolloClient.query(GetFeatureFlagsQuery()).fetchPolicy(FetchPolicy.CacheOnly).execute()
    return if (response.data != null) {
      GlobalScope.launch {
        // update in the background
        apolloClient.query(GetFeatureFlagsQuery()).fetchPolicy(FetchPolicy.NetworkOnly).execute()
      }
      // cached response
      response.data!!.featureFlags.toFeatureFlags()
    } else {
      // first time: get from the network, if network fails, use hardcoded defaults.
      apolloClient.query(GetFeatureFlagsQuery())
        .fetchPolicy(FetchPolicy.NetworkOnly)
        .execute().data?.featureFlags?.toFeatureFlags()
        ?: FeatureFlags(false, true)
    }
  }
}

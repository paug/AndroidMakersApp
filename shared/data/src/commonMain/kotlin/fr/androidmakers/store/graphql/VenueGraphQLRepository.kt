package fr.androidmakers.store.graphql

import at.asitplus.KmmResult
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VenueGraphQLRepository(private val apolloClient: ApolloClient): VenueRepository {
  override fun getVenue(id: String): Flow<KmmResult<Venue>> {
    return apolloClient.query(GetVenueQuery(id))
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.venue.toVenue()
        }
        .toResultFlow()
  }
}

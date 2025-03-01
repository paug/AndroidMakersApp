package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VenueGraphQLRepository(private val apolloClient: ApolloClient) : VenueRepository {
  override fun getVenue(id: String): Flow<Result<Venue>> {
    return apolloClient.query(GetVenueQuery(id))
      .cacheAndNetwork()
      .map { dataResult ->
        dataResult.map { data ->
          data.venue.toVenue()
        }
      }
  }
}

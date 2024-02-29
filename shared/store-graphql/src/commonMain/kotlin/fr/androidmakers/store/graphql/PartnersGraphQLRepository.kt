package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Logo
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.repo.PartnersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartnersGraphQLRepository(private val apolloClient: ApolloClient): PartnersRepository {
  override fun getPartners(): Flow<Result<List<Partner>>> {
    return apolloClient.query(GetPartnerGroupsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.partnerGroups.map {
            Partner(
                title = it.title,
                logos = it.partners.map {
                  Logo(
                      logoUrl = it.logoUrl,
                      name = it.name,
                      url = it.url
                  )
                }
            )
          }
        }
        .toResultFlow()
  }
}

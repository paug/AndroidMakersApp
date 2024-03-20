package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.watch
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.model.PartnerGroup
import fr.androidmakers.domain.repo.PartnersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartnersGraphQLRepository(private val apolloClient: ApolloClient): PartnersRepository {
  override fun getPartners(): Flow<Result<List<PartnerGroup>>> {
    return apolloClient.query(GetPartnerGroupsQuery())
        .fetchPolicy(FetchPolicy.CacheAndNetwork)
        .watch().map {
          it.dataAssertNoErrors.partnerGroups.map { partnerGroup ->
            PartnerGroup(
                title = partnerGroup.title,
                partners = partnerGroup.partners.map { partner ->
                  Partner(
                      logoUrl = partner.logoUrl,
                      name = partner.name,
                      url = partner.url
                  )
                }
            )
          }
        }
        .toResultFlow()
  }
}

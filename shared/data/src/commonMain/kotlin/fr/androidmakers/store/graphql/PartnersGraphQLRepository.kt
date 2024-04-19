package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.model.PartnerGroup
import fr.androidmakers.domain.repo.PartnersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartnersGraphQLRepository(private val apolloClient: ApolloClient): PartnersRepository {
  override fun getPartners(): Flow<Result<List<PartnerGroup>>> {
    return apolloClient.query(GetPartnerGroupsQuery())
        .cacheAndNetwork()
        .map {
          it.map {
            it.partnerGroups.map { partnerGroup ->
              PartnerGroup(
                title = partnerGroup.title,
                partners = partnerGroup.partners.map { partner ->
                  Partner(
                    logoUrl = partner.logoUrlLight,
                    name = partner.name,
                    url = partner.url,
                    logoUrlDark = partner.logoUrlDark
                  )
                }
              )
            }
          }
        }
  }
}

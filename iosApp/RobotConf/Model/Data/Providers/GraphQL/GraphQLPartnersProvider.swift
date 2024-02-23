//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Apollo
import Combine
import FirebaseCrashlytics

/// Object that provides partners from GraphQL
class GraphQLPartnersProvider {
    let partnersPublisher = PassthroughSubject<[PartnersByCategoryData], Error>()

    private let apolloClient: ApolloClient

    init(apolloClient: ApolloClient) {
        self.apolloClient = apolloClient
        apolloClient.fetch(query: GraphQLData.GetPartnersQuery(),
                           cachePolicy: .returnCacheDataAndFetch) { [weak self] result in
            guard let self = self else { return }
            do {
                let fetchedResult = try result.get()
                guard let partnerGroups = fetchedResult.data?.partnerGroups else {
                    print("Error getting GraphQL partners: \(fetchedResult.errors ?? [])")
                    let firstError: NSError = (fetchedResult.errors?.first as? NSError) ??
                        NSError(domain: "Empty data", code: 1)
                    self.partnersPublisher.send(completion: .failure(firstError))
                    return
                }
                var partnersByCategoryArray = [PartnersByCategoryData]()
                var categoryIndex = 0
                for partnerGroup in partnerGroups {
                    let category = PartnerCategoryData(order: categoryIndex, name: partnerGroup.title)
                    let partners = partnerGroup.partners.map {
                        PartnerData(logoUrl: $0.logoUrl, name: $0.name, url: $0.url)
                    }
                    partnersByCategoryArray.append(PartnersByCategoryData(category: category, partners: partners))
                    categoryIndex += 1
                }
                self.partnersPublisher.send(partnersByCategoryArray)
            } catch let error {
                Crashlytics.crashlytics().record(error: error)
                self.partnersPublisher.send(completion: .failure(error))
            }
        }
    }
}

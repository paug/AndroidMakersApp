//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Apollo
import Combine
import FirebaseCrashlytics

/// Object that provides venues from GraphQL
class GraphQLVenuesProvider: VenuesProvider {
    let confVenuePublisher = PassthroughSubject<VenueData, Error>()
    let partyVenuePublisher = PassthroughSubject<VenueData, Error>()

    private let apolloClient: ApolloClient

    init(apolloClient: ApolloClient) {
        self.apolloClient = apolloClient
        apolloClient.fetch(query: GraphQLData.GetVenuesQuery(),
                           cachePolicy: .returnCacheDataAndFetch) { [weak self] result in
            guard let self = self else { return }
            do {
                let fetchedResult = try result.get()
                guard let venues = fetchedResult.data?.venues else {
                    print("Error getting GraphQL venues: \(fetchedResult.errors ?? [])")
                    let firstError: NSError = (fetchedResult.errors?.first as? NSError) ??
                        NSError(domain: "Empty data", code: 1)
                    self.confVenuePublisher.send(completion: .failure(firstError))
                    self.partyVenuePublisher.send(completion: .failure(firstError))
                    return
                }
                for venue in venues {
                    if venue.id == "conference", let venueData = VenueData(from: venue) {
                        self.confVenuePublisher.send(venueData)
                    } else if venue.id == "afterparty", let venueData = VenueData(from: venue) {
                        self.partyVenuePublisher.send(venueData)
                    }
                }
            } catch let error {
                Crashlytics.crashlytics().record(error: error)
                self.confVenuePublisher.send(completion: .failure(error))
                self.partyVenuePublisher.send(completion: .failure(error))
            }
        }
    }
}

private extension VenueData {
    init?(from venue: GraphQLData.GetVenuesQuery.Data.Venue) {
        guard let address = venue.address,
              let imageUrl = venue.imageUrl else {
            return nil
        }
        self.address = address
        self.coordinates = venue.coordinates
        description = venue.description
        descriptionFr = venue.descriptionFr
        self.imageUrl = imageUrl
        name = venue.name
    }
}

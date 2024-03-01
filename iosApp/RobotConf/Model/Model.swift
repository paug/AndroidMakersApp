//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import shared

/// Singleton model
private(set) var model = Model()
private(set) var apolloClient = ApolloClientBuilder(
    url: "https://androidmakers-2023.ew.r.appspot.com/graphql",
    conference: "androidmakers2023",
    token: "").build()

#if DEBUG
private var mockModel = Model(dataProvider: DataProvider(desiredProviderType: .json))
/// Injects a mock model based on data provided by embedded json files in order to avoid backend dependencies.
func injectMockModel() {
    model = mockModel
}
#endif

protocol RepositoryProvider {
    var sessionRepository: SessionRepository { get }
    var venueRepository: VenueRepository { get }
    var feedbackRepository: FeedbackRepository { get }
    var partnersRepository: PartnersRepository { get }
}

/// The model API object
class Model: RepositoryProvider {
    private let dataProvider: DataProvider // A strong ref on the data provider must be kept
    let sessionRepository: SessionRepository
    let venueRepository: VenueRepository

    let feedbackRepository: FeedbackRepository

    let partnersRepository: PartnersRepository

    fileprivate init(dataProvider: DataProvider = DataProvider()) {
        self.dataProvider = dataProvider
        sessionRepository = SessionRepository(dataProvider: dataProvider)
        venueRepository = VenueRepository(dataProvider: dataProvider)

        feedbackRepository = FeedbackRepository(dataProvider: dataProvider)
        partnersRepository = PartnersGraphQLRepository(apolloClient: apolloClient)
    }
}

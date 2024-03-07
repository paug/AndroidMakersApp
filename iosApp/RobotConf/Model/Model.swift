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

private(set) var datastore = createDataStore(migrations: []) {
    do {
        let documentDir = try FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
        return documentDir.path.appending("/bookmarks.preferences_pb")
    } catch {
        return ""
    }
}

#if DEBUG
private var mockModel = Model(dataProvider: DataProvider(desiredProviderType: .json))
/// Injects a mock model based on data provided by embedded json files in order to avoid backend dependencies.
func injectMockModel() {
    model = mockModel
}
#endif

protocol RepositoryProvider {
    var sessionRepository: SessionRepository { get }
    var feedbackRepository: FeedbackRepository { get }
    var partnersRepository: PartnersRepository { get }
    var getConferenceVenueUC: GetConferenceVenueUseCase { get }
    var getAfterpartyVenueUC: GetAfterpartyVenueUseCase { get }
    var bookmarksRepository: BookmarksRepository { get }
}

/// The model API object
class Model: RepositoryProvider {
    private let dataProvider: DataProvider // A strong ref on the data provider must be kept
    let sessionRepository: SessionRepository

    let feedbackRepository: FeedbackRepository
    let getConferenceVenueUC: GetConferenceVenueUseCase
    let getAfterpartyVenueUC: GetAfterpartyVenueUseCase

    let partnersRepository: PartnersRepository
    let bookmarksRepository: BookmarksRepository

    fileprivate init(dataProvider: DataProvider = DataProvider()) {
        self.dataProvider = dataProvider
        sessionRepository = SessionRepository(dataProvider: dataProvider)
        let venueRepository = VenueGraphQLRepository(apolloClient: apolloClient)
        getConferenceVenueUC = GetConferenceVenueUseCase(venueRepository: venueRepository)
        getAfterpartyVenueUC = GetAfterpartyVenueUseCase(venueRepository: venueRepository)

        feedbackRepository = FeedbackRepository(dataProvider: dataProvider)
        partnersRepository = PartnersGraphQLRepository(apolloClient: apolloClient)

        bookmarksRepository = BookmarksDataStoreRepository(dataStore: datastore)
    }
}

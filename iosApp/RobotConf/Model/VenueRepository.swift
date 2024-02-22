//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Venue API
class VenueRepository {
    @Published private var conferenceVenue: Venue?
    @Published private var partyVenue: Venue?

    private var cancellables: Set<AnyCancellable> = []

    init(dataProvider: DataProvider) {
        dataProvider.confVenuePublisher
            .sink(receiveCompletion: { _ in
                self.conferenceVenue = nil
            }) { [unowned self] in
                self.conferenceVenue = $0
        }
        .store(in: &cancellables)

        dataProvider.partyVenuePublisher
            .sink(receiveCompletion: { _ in
                self.partyVenue = nil
            }) { [unowned self] in
                self.partyVenue = $0
        }
        .store(in: &cancellables)
    }

    func getConferenceVenue() -> AnyPublisher<Venue?, Never> {
        return $conferenceVenue.eraseToAnyPublisher()
    }

    func getPartyVenue() -> AnyPublisher<Venue?, Never> {
        return $partyVenue.eraseToAnyPublisher()
    }
}

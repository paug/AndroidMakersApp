//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides venues from a json file
class JsonVenuesProvider: VenuesProvider {
    let confVenuePublisher = PassthroughSubject<VenueData, Error>()
    let partyVenuePublisher = PassthroughSubject<VenueData, Error>()

    private struct Venues: Decodable {
        let conference: VenueData
        let afterparty: VenueData
    }

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "venues", withExtension: "json") else {
                fatalError("venues.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let venues = try decoder.decode(Venues.self, from: data)
                self.confVenuePublisher.send(venues.conference)
                self.partyVenuePublisher.send(venues.afterparty)
            } catch {
                self.confVenuePublisher.send(completion: .failure(error))
                self.partyVenuePublisher.send(completion: .failure(error))
            }
        }
    }
}

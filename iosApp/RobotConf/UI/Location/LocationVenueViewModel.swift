//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import CoreLocation
import shared

class LocationVenueViewModel: ObservableObject, Identifiable {
    enum VenueKind {
        case conference
        case party
    }

    struct Content {
        let name: String
        let detail: String
        let address: String
        let coordinates: String?
        let imageUrl: String
    }

    @Published var content: Content?

    @MainActor
    func activate() async {
    }
}

extension LocationVenueViewModel.Content {
    init(from venue: Venue) {
        self.init(
            name: venue.name,
            detail: Locale.current.languageCode != "fr" ? venue.description_ : venue.descriptionFr,
            address: venue.address,
            coordinates: venue.coordinates,
            imageUrl: venue.imageUrl)
    }
}

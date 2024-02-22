//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import CoreLocation

class LocationVenueViewModel: ObservableObject, Identifiable {
    enum VenueKind {
        case conference
        case party
    }

    struct Content {
        let name: String
        let description: String
        let address: String
        let coordinates: CLLocationCoordinate2D?
        let imageUrl: String
    }

    @Published var content: Content?

    private let venueRepo: VenueRepository
    private var disposables = Set<AnyCancellable>()

    init(kind: VenueKind, venueRepo: VenueRepository = model.venueRepository) {
        self.venueRepo = venueRepo
        let venuePublisher: AnyPublisher<Venue?, Never>
        switch kind {
        case .conference:
            venuePublisher = venueRepo.getConferenceVenue()
        case .party:
            venuePublisher = venueRepo.getPartyVenue()
        }
        venuePublisher.sink { [weak self] venue in
            guard let venue = venue else {
                // TODO: let the view know that the venue is unknown. To do that, maybe change the type of content
                // to be a type that can give an error
                return
            }

            self?.content = Content(from: venue)
        }.store(in: &disposables)
    }
}

private extension LocationVenueViewModel.Content {
    init(from venue: Venue) {
        self.init(name: venue.name,
                  description: Locale.current.languageCode != "fr" ? venue.description : venue.descriptionFr,
                  address: venue.address,
                  coordinates: venue.coordinates,
                  imageUrl: venue.imageUrl)
    }
}

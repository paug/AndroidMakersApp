//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of venues data
protocol VenuesProvider {
    /// Publisher of the conference venue
    var confVenuePublisher: PassthroughSubject<VenueData, Error> { get }
    /// Publisher of the party venue
    var partyVenuePublisher: PassthroughSubject<VenueData, Error> { get }
}

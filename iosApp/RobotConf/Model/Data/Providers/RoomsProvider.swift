//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of rooms data
protocol RoomsProvider {
    /// Publisher of rooms, indexed by their id
    var roomsPublisher: PassthroughSubject<[String: RoomData], Error> { get }
}

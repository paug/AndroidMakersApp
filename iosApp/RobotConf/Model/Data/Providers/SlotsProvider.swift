//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of slots data
protocol SlotsProvider {
    /// Publisher of slots
    var slotsPublisher: PassthroughSubject<[SlotData], Error> { get }
}

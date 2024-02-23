//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

/// A schedule slot that contains a link to a talk
struct SlotData: Decodable {
    let sessionId: String
    let roomId: String
    let startDate: Date
    let endDate: Date
}

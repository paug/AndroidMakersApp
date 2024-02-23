//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct VenueData: Decodable {
    let address: String
    let coordinates: String?
    let description: String
    let descriptionFr: String
    let imageUrl: String
    let name: String
}

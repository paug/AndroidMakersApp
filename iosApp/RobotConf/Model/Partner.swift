//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct Partner: Hashable {
    let name: String
    let logoUrl: URL
    let url: URL?
}

struct PartnerCategory: Hashable {
    let categoryName: String
    let partners: [Partner]
}

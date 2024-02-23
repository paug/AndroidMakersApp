//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of partners data
protocol PartnersProvider {
    /// Publisher of partners
    var partnersPublisher: CurrentValueSubject<[PartnersByCategoryData], Error> { get }
}

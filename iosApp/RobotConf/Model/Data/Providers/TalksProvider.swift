//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of talk data
protocol TalksProvider {
    /// Publisher of talks, indexed by their id
    var talksPublisher: PassthroughSubject<[String: TalkData], Error> { get }
}

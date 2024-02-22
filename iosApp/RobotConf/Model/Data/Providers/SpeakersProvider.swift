//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of speaker data
protocol SpeakersProvider {
    /// Publisher of speakers, indexed by their id
    var speakersPublisher: PassthroughSubject<[String: SpeakerData], Error> { get }
}

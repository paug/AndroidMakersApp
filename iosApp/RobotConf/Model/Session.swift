//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct Session: Hashable {
    enum Complexity {
        case beginner
        case intermediate
        case expert
    }
    let uid: String
    let title: String
    let description: String
    let duration: TimeInterval
    let speakers: [Speaker]
    let tags: [String]
    let startTime: Date
    let room: Room
    let language: Language
    let complexity: Complexity?
    let questionUrl: URL?
    let youtubeUrl: URL?
    let slidesUrl: URL?

    var isATalk: Bool { return !speakers.isEmpty }
}

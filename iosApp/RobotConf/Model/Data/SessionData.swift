//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct SessionData {
    enum Complexity {
        case beginner
        case intermediate
        case expert
    }
    struct Room {
        let id: String
        let name: String
        let index: Int
    }
    struct Speaker {
        let country: String?
        let name: String?
        let photoUrl: String?
        let bio: String?
        let shortBio: String?
        let company: String?
    }
    let uid: String
    let title: String
    let description: String
    let speakers: [Speaker]
    let tags: [String]
    let startTime: Date
    let endTime: Date
    let room: Room
    let language: String?
    let complexity: String?
    let questionUrl: String?
    let youtubeUrl: String?
    let slidesUrl: String?
}

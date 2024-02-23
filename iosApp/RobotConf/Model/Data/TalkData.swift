//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct TalkData: Decodable {
    let complexity: String?
    let speakers: [String]?
    let description: String
    let language: String?
    let title: String
    let tags: [String]
    let videoId: String?
    let presentation: String?
    let slido: String?
}

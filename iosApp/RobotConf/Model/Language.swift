//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

struct Language: OptionSet, Hashable {
    let rawValue: Int

    static let french = Language(rawValue: 1 << 0)
    static let english = Language(rawValue: 1 << 1)

    static let all: Language = [.french, .english]
}

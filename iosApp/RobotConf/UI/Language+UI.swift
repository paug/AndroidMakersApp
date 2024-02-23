//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

extension Language {
    var flagDescription: String {
        var flagDescription = ""
        if contains(.english) {
            flagDescription.append("🇬🇧")
        }
        if contains(.french) {
            flagDescription.append("🇫🇷")
        }
        return flagDescription
    }
}

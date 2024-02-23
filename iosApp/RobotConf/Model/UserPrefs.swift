//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

class UserPrefs {

    private let favoriteSessionsKey = "favoriteSessions"

    func getFavoriteSessions() -> [String] {
        return UserDefaults.standard.stringArray(forKey: favoriteSessionsKey) ?? []
    }

    func setFavoriteSessions(_ favoriteSessions: [String]) {
        UserDefaults.standard.set(favoriteSessions, forKey: favoriteSessionsKey)
    }
}

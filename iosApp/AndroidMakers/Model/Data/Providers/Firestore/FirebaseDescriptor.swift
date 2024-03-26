//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

/// Class to work with plist files that describe a Firebase instance
struct FirebaseDescriptor {
    /// Kind of Firestore descriptor
    enum Kind {
        /// Main descriptor, used to fetch all conference related data
        case main
        /// OpenFeedback descriptor
        case openFeedback

        var jsonFilename: String {
            switch self {
            case .main:
                return "GoogleService-Info"
            case .openFeedback:
                return "OpenFeedback-Info"
            }
        }
    }

    let googleAppId: String
    let gcmSenderID: String
    let projectId: String
    let bundleId: String
    let apiKey: String
    let clientId: String
    let storageBucket: String

    /// Loads a description for a given kind
    /// Returns nil if the file is missing or malformed
    init?(forKind kind: Kind) {
        guard let path = Bundle.main.path(forResource: kind.jsonFilename, ofType: "plist"),
            let config = NSDictionary(contentsOfFile: path),
            let googleAppId = config["GOOGLE_APP_ID"] as? String,
            let gcmSenderID = config["GCM_SENDER_ID"] as? String,
            let projectId = config["PROJECT_ID"] as? String,
            let bundleId = config["BUNDLE_ID"] as? String,
            let apiKey = config["API_KEY"] as? String,
            let clientId = config["CLIENT_ID"] as? String,
            let storageBucket = config["STORAGE_BUCKET"] as? String else {
            return nil
        }

        self.googleAppId = googleAppId
        self.gcmSenderID = gcmSenderID
        self.projectId = projectId
        self.bundleId = bundleId
        self.apiKey = apiKey
        self.clientId = clientId
        self.storageBucket = storageBucket
    }

    static func isEmbedded(forKind kind: Kind) -> Bool {
        guard let path = Bundle.main.path(forResource: kind.jsonFilename, ofType: "plist") else {
            print("⚠️ \(kind.jsonFilename).plist is not embedded.")
            return false
        }

        guard let config = NSDictionary(contentsOfFile: path),
            config.allKeys.count > 0 else {
            print("⚠️ \(kind.jsonFilename).plist is empty or not loadable.")
            return false
        }

        return true
    }
}

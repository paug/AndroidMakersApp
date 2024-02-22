//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import UIKit
import FirebaseAnalytics
import FirebaseCrashlytics
import Firebase

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.

        if FirebaseDescriptor.isEmbedded(forKind: .main) {
            FirebaseApp.configure()
            #if DEBUG
            Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(false)
            Analytics.setAnalyticsCollectionEnabled(false)
            #else
            Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(true)
            Analytics.setAnalyticsCollectionEnabled(true)
            #endif
        } else {
            print("⚠️ Firebase descriptor for the main purpose is not embedded, crashlytics disabled.")
        }

        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession,
                     options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after
        // application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
}

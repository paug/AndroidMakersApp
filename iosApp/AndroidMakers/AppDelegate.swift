//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import UIKit
import shared
import Firebase
import FirebaseMessaging
import UserNotifications

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {

    var window: UIWindow?
    private let feedHelper = FeedHelper()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

        FirebaseApp.configure()
        OpenFeedbackFirebaseConfigKt.initializeOpenFeedback(config: OpenFeedbackFirebaseConfig.companion.default(context: nil))
        DependenciesBuilder().inject(platformModules: [ViewModelModuleKt.viewModelModule])

        // Push notifications setup
        UNUserNotificationCenter.current().delegate = self
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, error in
            if let error = error {
                print("Notification permission error: \(error)")
            }
        }
        application.registerForRemoteNotifications()
        Messaging.messaging().delegate = self

        return true
    }

    // MARK: APNs Token

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }

    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("Failed to register for remote notifications: \(error)")
    }

    // MARK: MessagingDelegate

    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("FCM token: \(fcmToken ?? "nil")")
    }

    // MARK: UNUserNotificationCenterDelegate

    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        let userInfo = notification.request.content.userInfo
        saveFeedItemFromPayload(userInfo)
        completionHandler([.banner, .badge, .sound])
    }

    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        // Feed item already saved in willPresent or didReceiveRemoteNotification — no duplicate save here.
        completionHandler()
    }

    // MARK: Background data-only push

    func application(_ application: UIApplication,
                     didReceiveRemoteNotification userInfo: [AnyHashable: Any],
                     fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        // Only handle in background; foreground is covered by willPresent
        if application.applicationState != .active {
            saveFeedItemFromPayload(userInfo)
        }
        completionHandler(.newData)
    }

    // MARK: Feed storage

    private func saveFeedItemFromPayload(_ userInfo: [AnyHashable: Any]) {
        guard let title = userInfo["feed_title"] as? String,
              let body = userInfo["feed_body"] as? String else { return }
        let id = userInfo["feed_id"] as? String
        let type = userInfo["feed_type"] as? String
        feedHelper.saveFeedItem(id: id, type: type, title: title, body: body)
    }

    func applicationWillTerminate(_ application: UIApplication) {
        feedHelper.cancel()
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession,
                     options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
    }
}

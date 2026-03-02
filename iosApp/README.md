# Android Makers iOS app

This is the official Android Makers app for iOS.
The application shows the event schedule, talk details, venue and party information, and general information about the event.

It is fully open sourced, under MIT license. The UI is built with [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) (shared Kotlin code across Android, iOS, Desktop and Wear). The iOS target uses a minimal Swift integration layer (`AppDelegate`, `SceneDelegate`, `ContentView`) to bootstrap the Compose UI inside a `UIHostingController`.

You can find the iOS app [here on the AppStore](https://apps.apple.com/us/app/robotconf/id1502020576).


### Why it is named RobotConf?

Well, because Apple is what it is... We've been rejected from the Apple Store because of the usage of the `Android` term... So we decided to name it RobotConf instead.

### How to run it

To build and run the iOS app, open `Android Makers.xcodeproj` in Xcode.

You will need to provide access to 2 Firebase projects:

- One hosting the conference data
- One hosting the OpenFeedback data

To do that, replace the `GoogleService-Info.plist` and `OpenFeedback-Info.plist` with your own files.

If you want, you can host your own Firebase database using data coming from [our website](https://github.com/paug/android-makers-2020) (located in data/database). You'll also need to have an [OpenFeedback](https://openfeedback.io/) instance. Once you have it, add the `OpenFeedback-Info.plist` (representing the Firebase project that is hosting your Openfeedback data) to the project.

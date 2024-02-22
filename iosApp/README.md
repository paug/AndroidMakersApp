# Android Makers iOS app

This is the official Android Makers app for iOS.
The application shows the event schedule, talk details, venue and party information, and general information about the event.

It is fully open sourced, under MIT license. It is written in Swift, and it uses SwiftUI and Combine (this is why it is only available for iOS 13+).

You can find the iOS app [here on the AppStore](https://apps.apple.com/us/app/robotconf/id1502020576).


### Why it is named RobotConf?

Well, because Apple is what it is... We've been rejected from the Apple Store because of the usage of the `Android` term... So we decided to name it RobotConf instead 😆.

### How to run it
If you directly run it, the data is provided by the [embedded json files](https://github.com/paug/AndroidMakersApp-iOS/tree/master/RobotConf/Model/Data/Providers/Json/Resources). These files are only embedded in DEBUG (added by a Build Phase).

To use real files, you will need to provide to the app the access to 2 firebase projects:

- One hosting the conference data
- One hosting the OpenFeedback data

To do that, replace the `GoogleService-Info.plist` and `OpenFeedback-Info.plist` with your own files.

If you want, you can host your own Firebase database using data coming from [our website](https://github.com/paug/android-makers-2020) (located in data/database). You'll also need to have an [OpenFeedback](https://openfeedback.io/) instance. Once you have it, add the `OpenFeedback-Info.plist` (representing the Firebase project that is hosting your Openfeedback data) to the project.
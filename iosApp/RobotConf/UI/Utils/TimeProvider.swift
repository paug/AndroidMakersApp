//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

class TimeProvider {
    static let instance = TimeProvider()

    // In debug, consider the launch of the app as the 27 April 2023 14:36:52 GMT+02:00
    #if DEBUG
    private let fakeDate = Date(timeIntervalSince1970: 1682599012) // Thursday 27 April 2023 14:36:52 GMT+02:00
    private let realDate = Date()
    #endif

    /// Private constructor
    private init() { }

    var currentTime: Date {
        #if DEBUG
        return fakeDate.addingTimeInterval(Date().timeIntervalSince(realDate))
        #else
        return Date()
        #endif
    }
}

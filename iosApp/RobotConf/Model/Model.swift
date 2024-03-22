//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import shared

/// Singleton model
private(set) var model = Model()

#if DEBUG
private var mockModel = Model(dataProvider: DataProvider(desiredProviderType: .json))
/// Injects a mock model based on data provided by embedded json files in order to avoid backend dependencies.
func injectMockModel() {
    model = mockModel
}
#endif

protocol RepositoryProvider {
    var feedbackRepository: FeedbackRepository { get }
}

/// The model API object
class Model: RepositoryProvider {
    private let dataProvider: DataProvider // A strong ref on the data provider must be kept

    let feedbackRepository: FeedbackRepository

    fileprivate init(dataProvider: DataProvider = DataProvider()) {
        self.dataProvider = dataProvider
        feedbackRepository = FeedbackRepository(dataProvider: dataProvider)
    }
}

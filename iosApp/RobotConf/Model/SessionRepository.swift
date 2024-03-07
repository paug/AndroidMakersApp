//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import shared

/// Session API
class SessionRepository {
    @Published private(set) var sessions = [Session]()

    private var cancellables: Set<AnyCancellable> = []

    init(dataProvider: DataProvider) {
        dataProvider.sessionsPublisher
            .replaceError(with: [])
            .sink { [unowned self] in
                self.sessions = $0
        }
        .store(in: &cancellables)
    }

    func getSessions() -> AnyPublisher<[Session], Never> {
        return $sessions.eraseToAnyPublisher()
    }
}

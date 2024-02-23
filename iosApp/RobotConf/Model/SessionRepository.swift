//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Session API
class SessionRepository {
    @Published private(set) var sessions = [Session]()
    @Published private(set) var favoriteSessions = Set<String>()

    private let userPrefs = UserPrefs()
    private var cancellables: Set<AnyCancellable> = []

    init(dataProvider: DataProvider) {
        dataProvider.sessionsPublisher
            .replaceError(with: [])
            .sink { [unowned self] in
                self.sessions = $0
        }
        .store(in: &cancellables)

        favoriteSessions = Set(userPrefs.getFavoriteSessions())
    }

    func addSessionToFavorite(sessionId: String) {
        favoriteSessions.insert(sessionId)
        updateUserPrefs()
    }

    func removeSessionToFavorite(sessionId: String) {
        favoriteSessions.remove(sessionId)
        updateUserPrefs()
    }

    func getSessions() -> AnyPublisher<[Session], Never> {
        return $sessions.eraseToAnyPublisher()
    }

    func getFavoriteSessions() -> AnyPublisher<Set<String>, Never> {
        return $favoriteSessions.eraseToAnyPublisher()
    }

    private func updateUserPrefs() {
        userPrefs.setFavoriteSessions(Array(favoriteSessions))
    }
}

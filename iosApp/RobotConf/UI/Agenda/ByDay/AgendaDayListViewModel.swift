//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import shared

class AgendaDayListViewModel: ObservableObject, Identifiable {
    struct Content {
        struct Session: Hashable {
            enum State {
                case current
                case isComing
                case none
            }
            let uid: String
            let title: String
            let duration: TimeInterval
            let speakers: [Speaker]
            let room: String?
            let language: Language?
            let state: State

            var isATalk: Bool { return !speakers.isEmpty }
        }

        struct Section: Hashable {
            let date: Date
            var sessions: [Session]
            let isNewDay: Bool
        }

        var sections: [Section]
    }

    @Published var content: Content = Content(sections: [])
    @Published var favoriteSessions: Set<String> = []

    private var sessionRepo: SessionRepository
    private var bookmarksRepo: BookmarksRepository
    private var disposables = Set<AnyCancellable>()
    private var timer: Timer?
    private var isDisplayed = false

    init(
        sessionRepository: SessionRepository = model.sessionRepository,
        bookmarksRepository: BookmarksRepository = model.bookmarksRepository
    ) {
        self.sessionRepo = sessionRepository
        self.bookmarksRepo = bookmarksRepository
        sessionRepo.getSessions().sink { [weak self] in
            self?.sessionsChanged(sessions: $0)
        }.store(in: &disposables)
    }

    func viewAppeared() {
        isDisplayed = true
        timer = Timer.scheduledTimer(withTimeInterval: 60.0, repeats: true) { [weak self] _ in
            guard let self = self else { return }
            self.sessionsChanged(sessions: self.sessionRepo.sessions)
        }
        // recompute sessions in case status have changed
        sessionsChanged(sessions: self.sessionRepo.sessions)
    }

    @MainActor
    func activate() async {
        for await favoriteSessions in bookmarksRepo.getFavoriteSessions() {
            self.favoriteSessions = favoriteSessions
        }
    }

    func viewDisappeared() {
        timer?.invalidate()
        timer = nil
        isDisplayed = false
    }

    func toggleFavorite(ofSession session: Content.Session) {
        Task {
            let isBookmarked = !favoriteSessions.contains(session.uid)

            try await bookmarksRepo.setBookmarked(
                sessionId:session.uid,
                bookmarked:isBookmarked)
        }
    }

    private func sessionsChanged(sessions: [Session]) {
        let groupedSessions = Dictionary(grouping: sessions) { $0.startTime }
        let sortedKeys = groupedSessions.keys.sorted()
        var sections = [Content.Section]()
        var previousDate: Date?
        let calendar = Calendar.current
        sortedKeys.forEach { date in
            // can force unwrap since we're iterating amongst the keys
            let sessions = groupedSessions[date]!
                .sorted { $0.room.index < $1.room.index }
                .map { Content.Session(from: $0) }

            sections.append(Content.Section(
                date: date,
                sessions: sessions,
                isNewDay: !calendar.isDate(date, inSameDayAs: previousDate ?? Date.distantPast)))

            previousDate = date
        }
        content.sections = sections
    }
}

extension AgendaDayListViewModel.Content.Session {
    init(from session: Session) {
        self.init(uid: session.uid,
                  title: session.title,
                  duration: session.duration,
                  speakers: session.speakers.map { $0 },
                  room: session.isATalk ? session.room.name : nil,
                  language: session.isATalk ? session.language : nil,
                  state: State(from: session))
    }
}

private extension AgendaDayListViewModel.Content.Session.State {
    // Time before a session to be considered as coming
    private static let timeGapBeforeToCome = -5 * 60.0
    // Time after a session to be still considered as current
    private static let timeGapAfterCurrent = 2.5 * 60.0

    init(from session: Session) {
        let currentDate = TimeProvider.instance.currentTime

        let startToNow = currentDate.timeIntervalSince(session.startTime)
        switch startToNow {
        case Self.timeGapBeforeToCome..<0:
            self = .isComing
        case 0...(session.duration + Self.timeGapAfterCurrent):
            self = .current
        default:
            self = .none
        }
    }
}

//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

class AgendaDetailViewModel: ObservableObject, Identifiable {
    struct Content {
        let sessionId: String
        let title: String
        let startDate: Date
        let endDate: Date
        let description: String
        let tags: [String]
        let complexity: Session.Complexity?
        let speakers: [Speaker]
        let room: String?
        let language: Language
        let questionUrl: URL?
        let youtubeUrl: URL?
        let slidesUrl: URL?
        let isFavorite: Bool

        var isATalk: Bool { return !speakers.isEmpty }
    }

    @Published var content: Content?

    private var sessionRepo: SessionRepository
    private var disposables = Set<AnyCancellable>()

    init(sessionId: String, sessionRepo: SessionRepository = model.sessionRepository) {
        self.sessionRepo = sessionRepo
        sessionRepo.getSessions()
            .combineLatest(sessionRepo.getFavoriteSessions())
            .sink { [weak self] sessions, favorites in
                guard let session = sessions.first(where: { $0.uid == sessionId }) else {
                    // TODO: let the view know that this session is unknown. To do that, maybe change the type of
                    // content to be a type that can give an error
                    return
                }

                self?.content = Content(from: session, isFavorite: favorites.contains(sessionId))
        }.store(in: &disposables)
    }

    func toggleFavorite(ofSession session: Content) {
        if session.isFavorite {
            sessionRepo.removeSessionToFavorite(sessionId: session.sessionId)
        } else {
            sessionRepo.addSessionToFavorite(sessionId: session.sessionId)
        }
    }
}

private extension AgendaDetailViewModel.Content {
    init(from session: Session, isFavorite: Bool) {
        self.init(sessionId: session.uid,
                  title: session.title,
                  startDate: session.startTime,
                  endDate: session.startTime.addingTimeInterval(session.duration),
                  description: session.description,
                  tags: [], // session.tags is set to empty because it is all Android Development
                  complexity: session.complexity,
                  speakers: session.speakers,
                  room: session.isATalk ? session.room.name : nil,
                  language: session.language,
                  questionUrl: nil, // session.questionUrl, set it to nil for this year, to deactivate questions
                  youtubeUrl: session.youtubeUrl,
                  slidesUrl: session.slidesUrl,
                  isFavorite: isFavorite)
    }
}

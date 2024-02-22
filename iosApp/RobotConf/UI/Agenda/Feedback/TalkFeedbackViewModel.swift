//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

class TalkFeedbackViewModel: ObservableObject, Identifiable {
    struct Content {
        enum FeedbackAvailaibility {
            case notAvailable
            case available(TalkFeedback)
        }
        let title: String
        let availability: FeedbackAvailaibility
    }

    @Published var content: Content?

    private var feedbackRepo: FeedbackRepository
    private var disposables = Set<AnyCancellable>()

    init(talkId: String, sessionRepo: SessionRepository = model.sessionRepository,
         feedbackRepo: FeedbackRepository = model.feedbackRepository) {
        self.feedbackRepo = feedbackRepo
        sessionRepo.getSessions().first(where: { !$0.isEmpty })
            .combineLatest(feedbackRepo.getFeedbacks().first(where: { !$0.isEmpty }))
            .sink { [weak self] sessions, feedbacks in
                guard let session = sessions.first(where: { $0.uid == talkId }),
                    let feedback = feedbacks[talkId] else {
                    // TODO: let the view know that this talks is unknown. To do that, maybe change the type of content
                    // to be a type that can give an error
                    return
                }

                if session.startTime >= TimeProvider.instance.currentTime {
                    self?.content = Content(title: session.title, availability: .notAvailable)
                } else {
                    self?.content = Content(title: session.title, availability: .available(feedback))
                }
        }.store(in: &disposables)
    }
}

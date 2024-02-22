//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import UIKit

class AgendaFeedbackChoiceViewModel: ObservableObject, Identifiable {

    struct Content {
        struct RatioPosition {
            let horizontalRatio: Double
            let verticalRatio: Double

            fileprivate static func random() -> RatioPosition {
                return RatioPosition(horizontalRatio: Double.random(in: -1.0..<1.0),
                                     verticalRatio: Double.random(in: -1.0..<1.0))
            }
        }
        let title: String
        fileprivate let votes: [RatioPosition]
        fileprivate(set) var userVote: Content.RatioPosition?
        let availableColors: [UIColor]

        var userHasVoted: Bool { userVote != nil }

        var votePositions: [RatioPosition] {
            if let userVote = userVote {
                return votes + [userVote]
            }
            return votes
        }
    }

    @Published var content: Content

    let feedbackRepo: FeedbackRepository
    private let talkFeedback: TalkFeedback
    private let index: Int

    private let proposition: TalkFeedback.Proposition

    init(talkFeedback: TalkFeedback, index: Int, feedbackRepo: FeedbackRepository = model.feedbackRepository) {
        self.talkFeedback = talkFeedback
        self.index = index
        self.feedbackRepo = feedbackRepo
        self.proposition = talkFeedback.propositions[index]
        let propositionInfo = talkFeedback.propositionInfos[proposition]
        let numberOfVotes: Int
        let userVote: Content.RatioPosition?
        if let propositionInfo = propositionInfo {
            // numberOfVotes can sometimes be less than 0 so we need to clamp it to 0.
            numberOfVotes = max(propositionInfo.numberOfVotes - (propositionInfo.userHasVoted ? 1 : 0), 0)
            userVote = propositionInfo.userHasVoted ? Content.RatioPosition.random() : nil
        } else {
            numberOfVotes = 0
            userVote = nil
        }
        let votes = (0..<numberOfVotes).map { _ in
            Content.RatioPosition(horizontalRatio: Double.random(in: -1.0..<1.0),
                                  verticalRatio: Double.random(in: -1.0..<1.0))
        }

        content = Content(title: proposition.text, votes: votes,
                          userVote: userVote, availableColors: talkFeedback.colors)
    }

    func voteOrUnvote(triggeredFrom ratioPosition: Content.RatioPosition) {
        if content.userHasVoted {
            feedbackRepo.removeVote(proposition, for: talkFeedback.talkId)
            content.userVote = nil
        } else {
            feedbackRepo.vote(proposition, for: talkFeedback.talkId)
            content.userVote = ratioPosition
        }
    }

    private func computeVotes(from propositionInfo: TalkFeedback.PropositionInfo?) -> [Content.RatioPosition] {
        guard let propositionInfo = propositionInfo else { return [] }
        let numberOfVotes = propositionInfo.numberOfVotes - (propositionInfo.userHasVoted ? 1 : 0)
        return (0..<numberOfVotes).map { _ in
            Content.RatioPosition.random()
        }
    }
}

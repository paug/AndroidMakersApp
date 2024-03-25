//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import Apollo
import ApolloSQLite
import FirebaseFirestore
import UIKit

class GraphQLDataProvider: DataProviderProtocol {
    let votesPublisher: AnyPublisher<[String: TalkFeedback], Error>

    private let openFeedbackSynchronizer: OpenFeedbackSynchronizer?

    init() {
        openFeedbackSynchronizer = FirestoreOpenFeedbackSynchronizer()

        if let openFeedbackSynchronizer {
            votesPublisher = Publishers.CombineLatest3(
                openFeedbackSynchronizer.configPublisher,
                openFeedbackSynchronizer.sessionVotesPublisher,
                openFeedbackSynchronizer.userVotesPublisher)
            .map { config, sessionVotes, userVotes  in
                let preferredLanguage = Bundle.main.preferredLocalizations[0]
                let propositions = config.voteItems.sorted { $0.position ?? 0 < $1.position ?? 0 }
                    .compactMap { TalkFeedback.Proposition(from: $0, language: preferredLanguage) }
                let propositionDict = Dictionary(uniqueKeysWithValues: propositions.map { ($0.uid, $0) })
                let colors = config.chipColors.map { UIColor(named: $0) }

                var talkVotes = [String: TalkFeedback]()

                return talkVotes
            }.eraseToAnyPublisher()
        } else {
            votesPublisher = CurrentValueSubject([:]).eraseToAnyPublisher()
        }
    }

    func vote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        guard let openFeedbackSynchronizer = openFeedbackSynchronizer,
              let voteItm = openFeedbackSynchronizer.config?.voteItems.first(where: { $0.id == proposition.uid })
        else { return }
        openFeedbackSynchronizer.vote(voteItm, for: talkId)
    }

    func removeVote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        guard let openFeedbackSynchronizer = openFeedbackSynchronizer,
              let voteItm = openFeedbackSynchronizer.config?.voteItems.first(where: { $0.id == proposition.uid })
        else { return }
        openFeedbackSynchronizer.deleteVote(voteItm, of: talkId)
    }
}

private extension TalkFeedback.Proposition {
    init?(from voteItem: VoteConfigData.VoteItem, language: String) {
        guard voteItem.type == "boolean" else { return nil }
        self = TalkFeedback.Proposition(uid: voteItem.id, text: voteItem.languages?[language] ?? voteItem.name)
    }
}

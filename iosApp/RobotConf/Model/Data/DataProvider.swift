//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import CoreLocation
import UIKit
import Apollo

protocol DataProviderProtocol {
    var sessionsPublisher: AnyPublisher<[SessionData], Error> { get }
    var votesPublisher: AnyPublisher<[String: TalkFeedback], Error> { get }

    func vote(_ proposition: TalkFeedback.Proposition, for talkId: String)
    func removeVote(_ proposition: TalkFeedback.Proposition, for talkId: String)
}

/// Object that transforms and provides model data from server data
class DataProvider {
    enum ProviderType {
        case firestore
        case graphQL
        #if DEBUG
        case json
        #endif
    }

    var sessionsPublisher = PassthroughSubject<[Session], Error>()
    let votesPublisher: AnyPublisher<[String: TalkFeedback], Error>

    private var cancellables: Set<AnyCancellable> = []

    let proxyDataProvider: DataProviderProtocol

    init(desiredProviderType: ProviderType = .graphQL) {
        proxyDataProvider = GraphQLDataProvider()

        votesPublisher = proxyDataProvider.votesPublisher
    }

    func vote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        proxyDataProvider.vote(proposition, for: talkId)
    }

    func removeVote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        proxyDataProvider.removeVote(proposition, for: talkId)
    }
}

private extension Language {
    init(from str: String?) {
        guard let str = str else {
            self = .all
            return
        }
        var language: Language = []
        if str.localizedCaseInsensitiveContains("english") {
            language.formUnion(.english)
        }
        if str.localizedCaseInsensitiveContains("french") {
            language.formUnion(.french)
        }
        self = language
    }
}

private extension Session.Complexity {
    init?(from str: String?) {
        switch str {
        case "Beginner": self = .beginner
        case "Intermediate": self = .intermediate
        case "Expert": self = .expert
        default: return nil
        }
    }
}

private extension TalkFeedback.Proposition {
    init?(from voteItem: VoteConfigData.VoteItem, language: String) {
        guard voteItem.type == "boolean" else { return nil }
        self = TalkFeedback.Proposition(uid: voteItem.id, text: voteItem.languages?[language] ?? voteItem.name)
    }
}

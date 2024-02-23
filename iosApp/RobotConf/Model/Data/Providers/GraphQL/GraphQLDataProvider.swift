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
    let sessionsPublisher: AnyPublisher<[SessionData], Error>
    let confVenuePublisher: AnyPublisher<VenueData, Error>
    let partyVenuePublisher: AnyPublisher<VenueData, Error>
    let partnersPublisher: AnyPublisher<[PartnersByCategoryData], Error>
    let votesPublisher: AnyPublisher<[String: TalkFeedback], Error>

    private let venuesProvider: GraphQLVenuesProvider
    private let sessionsProvider: GraphQLTalksProvider
    private let partnersProvider: GraphQLPartnersProvider
    private let openFeedbackSynchronizer: OpenFeedbackSynchronizer?

    private let apolloClient: ApolloClient = {
        // setup cache
        let documentsPath = NSSearchPathForDirectoriesInDomains(
            .documentDirectory,
            .userDomainMask,
            true
        ).first!
        let documentsURL = URL(fileURLWithPath: documentsPath)
        let sqliteFileURL = documentsURL.appendingPathComponent("am_apollo_db.sqlite")
        let sqliteCache = try? SQLiteNormalizedCache(fileURL: sqliteFileURL)

        let endpointURL = URL(string: "https://androidmakers-2023.ew.r.appspot.com/graphql")!
        let store: ApolloStore
        if let sqliteCache {
            store = ApolloStore(cache: sqliteCache)
        } else {
            store = ApolloStore()
        }
        let interceptorProvider = NetworkInterceptorsProvider(
            interceptors: [ConfInterceptor(confUid: "androidmakers2023")],
            store: store
        )
        let networkTransport = RequestChainNetworkTransport(
            interceptorProvider: interceptorProvider, endpointURL: endpointURL
        )
        return ApolloClient(networkTransport: networkTransport, store: store)
    }()

    init() {
        venuesProvider = GraphQLVenuesProvider(apolloClient: apolloClient)
        confVenuePublisher = venuesProvider.confVenuePublisher.eraseToAnyPublisher()
        partyVenuePublisher = venuesProvider.partyVenuePublisher.eraseToAnyPublisher()

        partnersProvider = GraphQLPartnersProvider(apolloClient: apolloClient)
        partnersPublisher = partnersProvider.partnersPublisher.eraseToAnyPublisher()

        sessionsProvider = GraphQLTalksProvider(apolloClient: apolloClient)
        sessionsPublisher = sessionsProvider.talksPublisher.eraseToAnyPublisher()

        openFeedbackSynchronizer = FirestoreOpenFeedbackSynchronizer()

        if let openFeedbackSynchronizer {
            votesPublisher = Publishers.CombineLatest4(
                openFeedbackSynchronizer.configPublisher,
                openFeedbackSynchronizer.sessionVotesPublisher,
                openFeedbackSynchronizer.userVotesPublisher,
                sessionsPublisher)
            .map { config, sessionVotes, userVotes, sessions in
                let preferredLanguage = Bundle.main.preferredLocalizations[0]
                let propositions = config.voteItems.sorted { $0.position ?? 0 < $1.position ?? 0 }
                    .compactMap { TalkFeedback.Proposition(from: $0, language: preferredLanguage) }
                let propositionDict = Dictionary(uniqueKeysWithValues: propositions.map { ($0.uid, $0) })
                let colors = config.chipColors.map { UIColor(hexString: $0) }

                var talkVotes = [String: TalkFeedback]()
                sessions.forEach { session in
                    let talkId = session.uid
                    let votes = sessionVotes[talkId] ?? [:]
                    var infos = [TalkFeedback.Proposition: TalkFeedback.PropositionInfo]()
                    votes.forEach { vote in
                        if let proposition = propositionDict[vote.key] {
                            let identifier = UserVoteIdentifierData(
                                talkId: talkId, voteItemId: vote.key)
                            let hasVoted = userVotes[identifier]?.status == .active
                            infos[proposition] = TalkFeedback.PropositionInfo(numberOfVotes: vote.value,
                                                                              userHasVoted: hasVoted)
                        }
                    }
                    let talkVote = TalkFeedback(talkId: talkId, colors: colors, propositions: propositions,
                                                propositionInfos: infos)
                    talkVotes[talkId] = talkVote
                }
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

private class ConfInterceptor: ApolloInterceptor {
    let confUid: String

    init(confUid: String) {
        self.confUid = confUid
    }

    func interceptAsync<Operation>(
        chain: RequestChain,
        request: HTTPRequest<Operation>,
        response: HTTPResponse<Operation>?,
        completion: @escaping (Result<GraphQLResult<Operation.Data>, Error>) -> Void) where Operation : GraphQLOperation {
            request.addHeader(name: "conference", value: confUid)
            chain.proceedAsync(request: request, response: response, completion: completion)
    }

}

private class NetworkInterceptorsProvider: DefaultInterceptorProvider {

    let interceptors: [ApolloInterceptor]

    init(interceptors: [ApolloInterceptor], store: ApolloStore) {
        self.interceptors = interceptors
        super.init(store: store)
    }

    override func interceptors<Operation>(for operation: Operation) -> [ApolloInterceptor] where Operation : GraphQLOperation {
        var interceptors = super.interceptors(for: operation)
        self.interceptors.forEach { interceptor in
            interceptors.insert(interceptor, at: 0)
        }
        return interceptors
    }
}

private extension TalkFeedback.Proposition {
    init?(from voteItem: VoteConfigData.VoteItem, language: String) {
        guard voteItem.type == "boolean" else { return nil }
        self = TalkFeedback.Proposition(uid: voteItem.id, text: voteItem.languages?[language] ?? voteItem.name)
    }
}

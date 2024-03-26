//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import Firebase

/// Object that provides open feedback from a json file
class JsonOpenFeedbackSynchronizer: OpenFeedbackSynchronizer {
    let configPublisher = PassthroughSubject<VoteConfigData, Error>()
    let sessionVotesPublisher = PassthroughSubject<[String: [String: Int]], Error>()
    let userVotesPublisher = PassthroughSubject<[UserVoteIdentifierData: UserVoteData], Error>()

    private(set) var config: VoteConfigData?
    private(set) var userVotes = [UserVoteIdentifierData: UserVoteData]()

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "vote_configuration", withExtension: "json") else {
                fatalError("vote_configuration.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let config = try decoder.decode(VoteConfigData.self, from: data)
                self.config = config
                self.configPublisher.send(config)
            } catch {
                self.configPublisher.send(completion: .failure(error))
            }

            self.sessionVotesPublisher.send([
                "195718": [
                    "item1": 10,
                    "item2": 30,
                    "item3": 50,
                    "item4": 120]
            ])
            self.userVotesPublisher.send([:])
        }
    }

    func vote(_ voteItem: VoteConfigData.VoteItem, for talkId: String) {
        let userVoteIdentifier = UserVoteIdentifierData(talkId: talkId, voteItemId: voteItem.id)
        if let userVote = userVotes[userVoteIdentifier] {
            updateVote(userVote, status: .active)
        } else {
            createVote(voteItem, for: talkId)
        }
    }

    func deleteVote(_ voteItem: VoteConfigData.VoteItem, of talkId: String) {
        let userVoteIdentifier = UserVoteIdentifierData(talkId: talkId, voteItemId: voteItem.id)
        guard let userVote = userVotes[userVoteIdentifier] else { return }
        updateVote(userVote, status: .deleted)
    }

    private func createVote(_ voteItem: VoteConfigData.VoteItem, for talkId: String) {
        let userVoteIdentifier = UserVoteIdentifierData(talkId: talkId, voteItemId: voteItem.id)
        userVotes[userVoteIdentifier] = UserVoteData(
            createdAt: Timestamp(),
            id: UUID().uuidString,
            status: .active,
            talkId: talkId,
            updatedAt: Timestamp(),
            userId: "userId",
            voteItemId: voteItem.id)
        userVotesPublisher.send(userVotes)
    }

    private func updateVote(_ vote: UserVoteData, status: UserVoteData.Status) {
        let userVoteIdentifier = UserVoteIdentifierData(talkId: vote.talkId, voteItemId: vote.voteItemId)
        userVotes[userVoteIdentifier] = UserVoteData(
            createdAt: vote.createdAt,
            id: vote.id,
            status: status,
            talkId: vote.talkId,
            updatedAt: vote.updatedAt,
            userId: vote.userId,
            voteItemId: vote.voteItemId)
        userVotesPublisher.send(userVotes)
    }
}

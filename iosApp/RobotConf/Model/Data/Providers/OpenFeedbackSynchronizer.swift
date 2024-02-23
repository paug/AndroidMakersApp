//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// A provider of openfeedback data
protocol OpenFeedbackSynchronizer {
    /// Publisher of the feedback configuration
    var configPublisher: PassthroughSubject<VoteConfigData, Error> { get }
    /// Publisher of the vote counts indexed by vote item indexed by session
    var sessionVotesPublisher: PassthroughSubject<[String: [String: Int]], Error> { get }
    /// Publisher of user votes indexed by user vote identifier
    var userVotesPublisher: PassthroughSubject<[UserVoteIdentifierData: UserVoteData], Error> { get }

    var config: VoteConfigData? { get }
    var userVotes: [UserVoteIdentifierData: UserVoteData] { get }

    /// Vote for a given vote in a given talk
    ///
    /// - Parameters:
    ///   - voteItem: the vote
    ///   - talkId: the talk for which this vote is
    func vote(_ voteItem: VoteConfigData.VoteItem, for talkId: String)

    /// Delete an existing user vote in a given talk
    ///
    /// - Parameters:
    ///   - voteItem: the vote
    ///   - talkId: the talk for which this vote is
    func deleteVote(_ voteItem: VoteConfigData.VoteItem, of talkId: String)
}

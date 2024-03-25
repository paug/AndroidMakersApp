//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Firebase
import CodableFirebase
import Combine
import FirebaseCrashlytics
import FirebaseAuth

class FirestoreOpenFeedbackSynchronizer: OpenFeedbackSynchronizer {

    private static let projectId = "am2023"

    let configPublisher = PassthroughSubject<VoteConfigData, Error>()
    let sessionVotesPublisher = PassthroughSubject<[String: [String: Int]], Error>()
    let userVotesPublisher = PassthroughSubject<[UserVoteIdentifierData: UserVoteData], Error>()

    private(set) var config: VoteConfigData?
    private(set) var userVotes = [UserVoteIdentifierData: UserVoteData]()

    var userId: String?

    private let database: Firestore
    init?() {
        guard let openFeedbackDescriptor = FirebaseDescriptor(forKind: .openFeedback) else {
            print("Error: please embed a valid OpenFeedback-Info file in the main bundle")
            return nil
        }

        // Configure with manual options.
        let secondaryOptions = FirebaseOptions(googleAppID: openFeedbackDescriptor.googleAppId,
                                               gcmSenderID: openFeedbackDescriptor.gcmSenderID)
        secondaryOptions.projectID = openFeedbackDescriptor.projectId
        secondaryOptions.bundleID = openFeedbackDescriptor.bundleId
        secondaryOptions.apiKey = openFeedbackDescriptor.apiKey
        secondaryOptions.clientID = openFeedbackDescriptor.clientId
        secondaryOptions.storageBucket = openFeedbackDescriptor.storageBucket

        // Configure an alternative FIRApp.
        FirebaseApp.configure(name: "OpenFeedback", options: secondaryOptions)

        // Retrieve a previous created named app.
        guard let openFeedbackApp = FirebaseApp.app(name: "OpenFeedback") else {
            assert(false, "Could not retrieve openFeedback app")
            return nil
        }

        // Retrieve a Real Time Database client configured against a specific app.
        database = Firestore.firestore(app: openFeedbackApp)

        getConfig()
        getSessionsFeedbacks()

        Auth.auth(app: openFeedbackApp).signInAnonymously { authResult, _ in
            guard let user = authResult?.user else { return }
            self.userId = user.uid
            self.getUserFeedbacks(user: user)
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

    private func getUserFeedbacks(user: User) {
        database.collection("projects/\(Self.projectId)/userVotes").whereField("userId", isEqualTo: user.uid)
            .addSnapshotListener { [weak self] (querySnapshot, err) in
                guard let self = self else { return }
                if let err = err {
                    print("Error getting feedback documents: \(err)")
                    self.userVotesPublisher.send(completion: .failure(err))
                } else {
                    do {
                        self.userVotes = [:]
                        for document in querySnapshot!.documents {
                            let userVote = try FirestoreDecoder().decode(UserVoteData.self, from: document.data())
                            let userVoteIdentifier = UserVoteIdentifierData(
                                talkId: userVote.talkId, voteItemId: userVote.voteItemId)
                            self.userVotes[userVoteIdentifier] = userVote
                        }
                        self.userVotesPublisher.send(self.userVotes)
                    } catch let error {
                        Crashlytics.crashlytics().record(error: error)
                        self.userVotesPublisher.send(completion: .failure(error))
                    }
                }
        }
    }

    private func getSessionsFeedbacks() {
        database.collection("projects/\(Self.projectId)/sessionVotes")
            .addSnapshotListener { [weak self] (querySnapshot, err) in

                guard let self = self else { return }
                if let err = err {
                    print("Error getting session votes documents: \(err)")
                    self.sessionVotesPublisher.send(completion: .failure(err))
                } else {
                    do {
                        var sessionVotes = [String: [String: Int]]()
                        for document in querySnapshot!.documents {
                            // due to a crash when converting document.data() to a json, we exclude the rows that we
                            // don't want (i.e. every row that does not qualifies a voteItem that is a preconfigured
                            // vote option). This will filter out any textual vote.
                            let data = document.data().filter { $0.value is Int }
                            sessionVotes[document.documentID] = try document.decoded(data: data)
                        }
                        self.sessionVotesPublisher.send(sessionVotes)
                    } catch let error {
                        Crashlytics.crashlytics().record(error: error)
                        self.sessionVotesPublisher.send(completion: .failure(error))
                    }
                }
        }
    }

    private func getConfig() {
        database.collection("projects")
            .document(Self.projectId)
            .addSnapshotListener { [weak self] document, err in
                guard let self = self else { return }
                if let err = err {
                    print("Error getting open feedback project document: \(err)")
                    self.config = nil
                    self.configPublisher.send(completion: .failure(err))
                } else {
                    do {
                        let config: VoteConfigData = try FirestoreDecoder()
                            .decode(VoteConfigData.self, from: document?.data() ?? [:])
                        self.config = config
                        self.configPublisher.send(config)
                    } catch let error {
                        Crashlytics.crashlytics().record(error: error)
                        self.config = nil
                        self.configPublisher.send(completion: .failure(error))
                    }
                }
        }
    }

    private func createVote(_ voteItem: VoteConfigData.VoteItem, for talkId: String) {
        guard let userId = userId else {
            return
        }
        let uid = database.collection("projects/\(Self.projectId)/userVotes").document().documentID
        let date = Date()
        database.collection("projects/\(Self.projectId)/userVotes").document(uid).setData([
            "createdAt": date,
            "id": uid,
            "projectId": Self.projectId,
            "status": "active",
            "talkId": talkId,
            "updatedAt": date,
            "userId": userId,
            "voteItemId": voteItem.id
        ]) { err in
            if let err = err {
                Crashlytics.crashlytics().record(error: err)
                print("Error writing document: \(err)")
            } else {
                print("Document successfully written!")
            }
        }
    }

    private func updateVote(_ vote: UserVoteData, status: UserVoteData.Status) {
        database.collection("projects/\(Self.projectId)/userVotes").document(vote.id).setData([
            "createdAt": vote.creationDate,
            "id": vote.id,
            "projectId": Self.projectId,
            "status": status.rawValue,
            "talkId": vote.talkId,
            "updatedAt": Date(),
            "userId": vote.userId,
            "voteItemId": vote.voteItemId
        ]) { err in
            if let err = err {
                Crashlytics.crashlytics().record(error: err)
                print("Error writing document: \(err)")
            } else {
                print("Document successfully written!")
            }
        }
    }
}

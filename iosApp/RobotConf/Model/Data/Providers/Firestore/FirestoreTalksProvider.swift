//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import FirebaseCrashlytics

/// Object that provides talks from Firestore
class FirestoreTalksProvider: TalksProvider {
    var talksPublisher = PassthroughSubject<[String: TalkData], Error>()

    init(database: Firestore) {
        database.collection("sessions").getDocuments { [weak self] (querySnapshot, err) in
            guard let self = self else { return }
            if let err = err {
                print("Error getting sessions documents: \(err)")
                self.talksPublisher.send(completion: .failure(err))
            } else {
                do {
                    var talks = [String: TalkData]()
                    for document in querySnapshot!.documents {
                        talks[document.documentID] = try document.decoded()
                    }
                    self.talksPublisher.send(talks)
                } catch let error {
                    Crashlytics.crashlytics().record(error: error)
                    self.talksPublisher.send(completion: .failure(error))
                }
            }
        }
    }
}

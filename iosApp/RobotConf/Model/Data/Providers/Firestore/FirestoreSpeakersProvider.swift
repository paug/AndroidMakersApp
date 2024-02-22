//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import FirebaseCrashlytics

/// Object that provides speakers from Firestore
class FirestoreSpeakersProvider: SpeakersProvider {
    var speakersPublisher = PassthroughSubject<[String: SpeakerData], Error>()

    init(database: Firestore) {
        database.collection("speakers").getDocuments { [weak self] (querySnapshot, err) in
            guard let self = self else { return }
            if let err = err {
                print("Error getting speakers documents: \(err)")
                self.speakersPublisher.send(completion: .failure(err))
            } else {
                do {
                    var speakers = [String: SpeakerData]()
                    for document in querySnapshot!.documents {
                        speakers[document.documentID] = try document.decoded()
                    }
                    self.speakersPublisher.send(speakers)
                } catch let error {
                    Crashlytics.crashlytics().record(error: error)
                    self.speakersPublisher.send(completion: .failure(error))
                }
            }
        }
    }
}

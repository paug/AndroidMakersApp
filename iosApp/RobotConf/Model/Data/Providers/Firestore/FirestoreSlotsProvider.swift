//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import FirebaseCrashlytics

/// Object that provides slots from Firestore
class FirestoreSlotsProvider: SlotsProvider {
    private struct Slots: Decodable {
        let all: [SlotData]
    }

    var slotsPublisher = PassthroughSubject<[SlotData], Error>()

    init(database: Firestore) {
        database.collection("schedule-app").document("slots").getDocument { [weak self] (document, err) in
            guard let self = self else { return }
            if let err = err {
                print("Error getting schedule slots documents: \(err)")
                self.slotsPublisher.send(completion: .failure(err))
            } else {
                do {
                    let slots: Slots = try document!.decoded()
                    self.slotsPublisher.send(slots.all)
                } catch let error {
                    Crashlytics.crashlytics().record(error: error)
                    self.slotsPublisher.send(completion: .failure(error))
                }
            }
        }
    }
}

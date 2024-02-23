//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import FirebaseCrashlytics

/// Object that provides rooms from Firestore
class FirestoreRoomsProvider: RoomsProvider {
    private struct Rooms: Decodable {
        let allRooms: [RoomData]
    }

    var roomsPublisher = PassthroughSubject<[String: RoomData], Error>()

    init(database: Firestore) {
        database.collection("schedule-app").document("rooms").getDocument { [weak self] (document, err) in
            guard let self = self else { return }
            if let err = err {
                print("Error getting schedule rooms documents: \(err)")
                self.roomsPublisher.send(completion: .failure(err))
            } else {
                do {
                    let rooms: Rooms = try document!.decoded()
                    self.roomsPublisher.send(Dictionary(uniqueKeysWithValues: rooms.allRooms.map { ($0.roomId, $0) }))
                } catch let error {
                    Crashlytics.crashlytics().record(error: error)
                    self.roomsPublisher.send(completion: .failure(error))
                }
            }
        }
    }
}

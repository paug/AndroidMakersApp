//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides rooms from a json file
class JsonRoomsProvider: RoomsProvider {
    private struct RoomsContainer: Decodable {
        struct Rooms: Decodable {
            let allRooms: [RoomData]
        }
        let rooms: Rooms
    }

    var roomsPublisher = PassthroughSubject<[String: RoomData], Error>()

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "schedule-app", withExtension: "json") else {
                fatalError("schedule-app.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let roomsContainer = try decoder.decode(RoomsContainer.self, from: data)
                let rooms = Dictionary(uniqueKeysWithValues: roomsContainer.rooms.allRooms.map { ($0.roomId, $0) })
                self.roomsPublisher.send(rooms)
            } catch {
                self.roomsPublisher.send(completion: .failure(error))
            }
        }
    }
}

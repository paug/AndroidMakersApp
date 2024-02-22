//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides slots from a json file
class JsonSlotsProvider: SlotsProvider {
    private struct SlotsContainer: Decodable {
        struct Slots: Decodable {
            let all: [SlotData]
        }
        let slots: Slots
    }

    var slotsPublisher = PassthroughSubject<[SlotData], Error>()

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "schedule-app", withExtension: "json") else {
                fatalError("schedule-app.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                decoder.dateDecodingStrategy = .iso8601
                let slotsContainer = try decoder.decode(SlotsContainer.self, from: data)
                self.slotsPublisher.send(slotsContainer.slots.all)
            } catch {
                self.slotsPublisher.send(completion: .failure(error))
            }
        }
    }
}

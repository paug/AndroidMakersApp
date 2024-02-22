//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides talks from a json file
class JsonTalksProvider: TalksProvider {
    var talksPublisher = PassthroughSubject<[String: TalkData], Error>()

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "sessions", withExtension: "json") else {
                fatalError("sessions.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let talks = try decoder.decode([String: TalkData].self, from: data)
                self.talksPublisher.send(talks)
            } catch {
                self.talksPublisher.send(completion: .failure(error))
            }
        }
    }
}

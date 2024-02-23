//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides speakers from a json file
class JsonSpeakersProvider: SpeakersProvider {
    var speakersPublisher = PassthroughSubject<[String: SpeakerData], Error>()

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "speakers", withExtension: "json") else {
                fatalError("speakers.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let speakers = try decoder.decode([String: SpeakerData].self, from: data)
                self.speakersPublisher.send(speakers)
            } catch {
                self.speakersPublisher.send(completion: .failure(error))
            }
        }
    }
}

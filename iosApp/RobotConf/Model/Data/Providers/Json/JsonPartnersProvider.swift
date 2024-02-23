//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Object that provides partners from a json file
class JsonPartnersProvider: PartnersProvider {
    var partnersPublisher = CurrentValueSubject<[PartnersByCategoryData], Error>([])

    init() {
        DispatchQueue.main.async {
            guard let url = Bundle.main.url(forResource: "partners", withExtension: "json") else {
                fatalError("partners.json is not embedded in the app.")
            }
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                let partners = try decoder.decode([PartnersByCategoryData].self, from: data)
                self.partnersPublisher.send(partners)
            } catch {
                self.partnersPublisher.send(completion: .failure(error))
            }
        }
    }
}

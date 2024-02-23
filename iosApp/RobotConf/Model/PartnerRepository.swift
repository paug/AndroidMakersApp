//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

/// Partner list API
class PartnerRepository {
    @Published private var partners = [PartnerCategory]()

    private var cancellables: Set<AnyCancellable> = []

    init(dataProvider: DataProvider) {
        dataProvider.partnerPublisher
            .replaceError(with: [])
            .sink { [unowned self] in
                self.partners = $0
        }
        .store(in: &cancellables)
    }

    func getPartners() -> AnyPublisher<[PartnerCategory], Never> {
        return $partners.eraseToAnyPublisher()
    }
}

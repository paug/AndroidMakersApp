//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import UIKit
import shared

class AboutViewModel: ObservableObject, Identifiable {

    @Published var partnerGroups = [PartnerGroup]()

    private let deps = DepContainer()

    @MainActor
    func activate() async {
        let partnersFlow = deps.getPartnersUseCase.invoke()
        for await partnerResult in partnersFlow {
            partnerResult.fold(
                onSuccess: { [weak self] partnerGroups in
                    self?.partnerGroups = partnerGroups as? [PartnerGroup] ?? []
                },
                onFailure: { error in
                    print("Error in retrieving partners")
                }
            )
        }
    }

    func openTwitterPage() {
        deps.openXAccountUseCase.invoke()
    }

    func openHashtagPage() {
        deps.openXHashtagUseCase.invoke()
    }

    func openYoutubePage() {
        deps.openYoutubeUseCase.invoke()
    }

    func openPartnerPage(_ partner: Partner) {
        if let url = URL(string: partner.url) {
            UIApplication.shared.open(url)
        }
    }
}

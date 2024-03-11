//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine
import UIKit
import shared

class AboutViewModel: ObservableObject, Identifiable {

    @Published var partnerGroups = [PartnerGroup]()

    private var disposables = Set<AnyCancellable>()

    @MainActor
    func activate() async {
        let partnersFlow = model.partnersRepository.getPartners()
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
        model.openXAccountUC.invoke()
    }

    func openHashtagPage() {
        model.openXHashtagUC.invoke()
    }

    func openYoutubePage() {
        model.openYoutubeUC.invoke()
    }

    func openPartnerPage(_ partner: Partner) {
        if let url = URL(string: partner.url) {
            UIApplication.shared.open(url)
        }
    }
}

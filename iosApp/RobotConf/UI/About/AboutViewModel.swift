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
        let screenName = "@AndroidMakersFR"
        let appURL = URL(string: "twitter://user?screen_name=\(screenName)")!
        let webURL = URL(string: "https://twitter.com/\(screenName)")!

        let application = UIApplication.shared

        if application.canOpenURL(appURL) {
             application.open(appURL)
        } else {
             application.open(webURL)
        }
    }

    func openHashtagPage() {
        let screenName = "AndroidMakers"
        let appURL = URL(string: "twitter://search?query=\(screenName)")!
        let webURL = URL(string: "https://twitter.com/search?q=\(screenName)")!

        let application = UIApplication.shared

        if application.canOpenURL(appURL) {
             application.open(appURL)
        } else {
             application.open(webURL)
        }
    }

    func openYoutubePage() {
        let user =  "UCkatLlah5weIpN23LqMgdTg"
        let appURL = URL(string: "youtube://www.youtube.com/channel/\(user)")!
        let webURL = URL(string: "https://www.youtube.com/channel/\(user)")!
        let application = UIApplication.shared

        if application.canOpenURL(appURL) {
            application.open(appURL)
        } else {
            // if Youtube app is not installed, open URL inside Safari
            application.open(webURL)
        }
    }

    func openPartnerPage(_ partner: Partner) {
        if let url = URL(string: partner.url) {
            UIApplication.shared.open(url)
        }
    }
}

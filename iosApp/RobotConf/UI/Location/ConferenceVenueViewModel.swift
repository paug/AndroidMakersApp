//
//  ConferenceVenueViewModel.swift
//  RobotConf
//
//  Created by Antoine Robiez on 01/03/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import Foundation
import shared

class ConferenceVenueViewModel: LocationVenueViewModel {
    let getConferenceVenueUc: GetConferenceVenueUseCase

    init(getConferenceVenueUc: GetConferenceVenueUseCase) {
        self.getConferenceVenueUc = getConferenceVenueUc
    }

    override func activate() async {
        for await confVenueResult in self.getConferenceVenueUc.invoke() {
            confVenueResult.fold(
                onSuccess: { [weak self] venue in
                    self?.content = venue != nil ? Content(from: venue!) : nil
                },
                onFailure: { _ in
                    print("Error in getting venue")
                }
            )
        }
    }
}

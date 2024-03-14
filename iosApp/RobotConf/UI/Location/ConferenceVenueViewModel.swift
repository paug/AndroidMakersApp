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

    override func activate() async {
        let getConferenceVenueUc = DepContainer().getConferenceVenueUseCase
        for await confVenueResult in getConferenceVenueUc.invoke() {
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

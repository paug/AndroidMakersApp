//
//  AfterPartyVenueViewModel.swift
//  RobotConf
//
//  Created by Antoine Robiez on 01/03/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import Foundation
import shared

class AfterPartyVenueViewModel: LocationVenueViewModel {


    override func activate() async {
        let getAfterpartyUc = DepContainer().getAfterpartyVenueUseCase
        for await afterPartyVenueResult in getAfterpartyUc.invoke() {
            afterPartyVenueResult.fold(
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

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
    
    let getAfterpartyUc: GetAfterpartyVenueUseCase

    init(getAfterpartyUc: GetAfterpartyVenueUseCase) {
        self.getAfterpartyUc = getAfterpartyUc
    }

    override func activate() async {
        for await afterPartyVenueResult in self.getAfterpartyUc.invoke() {
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

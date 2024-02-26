//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct LocationListView: View {
    var body: some View {
        NavigationView {
            List {
                NavigationLink(destination: LocationVenueView(kind: .conference)) {
                    Text(stringResource(MR.strings().venue_conference_tab))
                }
                NavigationLink(destination: LocationVenueView(kind: .party)) {
                    Text(stringResource(MR.strings().venue_afterparty_tab))
                }
                NavigationLink(destination: LocationPlanView()) {
                    Text(stringResource(MR.strings().venue_floor_plan_tab))
                }
            }.navigationBarTitle(Text(stringResource(MR.strings().venue)), displayMode: .large)
            // Display the LocationVenueView in the detail view to avoid having a white screen
            // (this will be only used when the master and detail are visible together i.e. on ipads)
            LocationVenueView(kind: .conference)
        }
    }
}

#if DEBUG
struct LocationListView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return LocationListView()
    }
}
#endif

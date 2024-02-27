//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct LocationPlanView: View {
    var body: some View {
        Image("plan")
            .resizable()
            .aspectRatio(contentMode: .fit)
            .navigationBarTitle(Text(stringResource(MR.strings().venue_floor_plan_tab)), displayMode: .inline)
    }
}

struct LocationPlanView_Previews: PreviewProvider {
    static var previews: some View {
        LocationPlanView()
    }
}

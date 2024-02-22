//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI

struct LocationPlanView: View {
    var body: some View {
        Image("plan")
            .resizable()
            .aspectRatio(contentMode: .fit)
            .navigationBarTitle(Text(L10n.Locations.plan), displayMode: .inline)
    }
}

struct LocationPlanView_Previews: PreviewProvider {
    static var previews: some View {
        LocationPlanView()
    }
}

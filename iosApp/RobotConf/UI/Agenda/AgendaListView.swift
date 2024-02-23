//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import SwiftUI

struct AgendaListView: View {
    @Environment(\.horizontalSizeClass) var horizontalSizeClass

    var body: some View {
        GeometryReader { geometry in
            if geometry.size.width < 600 {
                AgendaDayListView()
            } else {
                AgendaRoomListView()
            }
        }
    }
}

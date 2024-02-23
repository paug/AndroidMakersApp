//
//  Copyright © 2023 Paris Android User Group. All rights reserved.
//

import Foundation
import SwiftUI

extension View {
    /// Apply a list style inset grouped if available, grouped otherwise
    @ViewBuilder func insetGroupedListStyle() -> some View {
        if #available(iOS 14, *) {
            self.listStyle(.insetGrouped)
        } else {
            self.listStyle(.grouped)
        }
    }
}

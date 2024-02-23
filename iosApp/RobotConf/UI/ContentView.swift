//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    @State private var selection = 0

    var body: some View {
        TabView(selection: $selection) {
            AgendaListView()
                .tabItem {
                    VStack {
                        Image("agenda")
                        Text(stringResource(MR.strings().agenda))
                    }
            }.tag(0)

             LocationListView()
                .tabItem {
                    VStack {
                        Image("location")
                        Text(stringResource(MR.strings().venue))
                    }
            }.tag(1)

            AboutView()
                .tabItem {
                    VStack {
                        Image("about")
                        Text(stringResource(MR.strings().about))
                    }
            }.tag(2)
        }
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return ContentView()
    }
}
#endif

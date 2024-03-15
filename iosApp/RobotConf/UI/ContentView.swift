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

            SpeakersView()
                .tabItem {
                    VStack {
                        Image(systemName: "person.3.fill")
                        Text(stringResource(MR.strings().speakers))
                    }
                }.tag(2)

            SponsorsView()
                .tabItem {
                    VStack {
                        Image(systemName: "seal")
                        Text(stringResource(MR.strings().sponsors))
                    }
                }.tag(3)

            AboutView()
                .tabItem {
                    VStack {
                        Image("about")
                        Text(stringResource(MR.strings().about))
                    }
            }.tag(4)
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

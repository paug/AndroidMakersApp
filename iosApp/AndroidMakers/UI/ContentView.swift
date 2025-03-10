//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        MainView()
            .ignoresSafeArea(.all, edges: [.top, .bottom])
    }
}

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainLayoutViewControllerKt.MainLayoutViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

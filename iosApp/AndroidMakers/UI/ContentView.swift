//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        MainView()
    }
}

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainLayoutViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

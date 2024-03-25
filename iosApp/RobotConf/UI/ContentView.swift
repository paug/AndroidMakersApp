//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    @State private var selection = 0

    var body: some View {
        MainView()
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

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainLayoutViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

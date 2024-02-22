//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import SafariServices

/// A button that opens a link. This link can be opened in a modal SFSafariWebview, or in the device browser.
struct WebButton<Label: View>: View {
    // A view that describes the effect of calling `action`.
    private var label: () -> Label

    // initial URL string
    private let url: URL

    /// Whether link should be opened in an external browser or in a modal embedded SFSafariWebview
    private var openInExternalBrowser: Bool

    // whether or not to show the Safari ViewController
    @State private var showSafari = false

    /// Create the button
    /// - Parameters:
    ///   - url: the url that the button should open when it is clicked
    ///   - openInExternalBrowser: Whether link should be opened in an external browser or in a modal SFSafariWebview.
    ///                            Default to false.
    ///   - label: A view that describes the effect of calling the `action` of the button.
    init(url: URL, openInExternalBrowser: Bool = false, @ViewBuilder label: @escaping () -> Label) {
        self.url = url
        self.openInExternalBrowser = openInExternalBrowser
        self.label = label
    }

    var body: some View {
        Button(action: {
            if openInExternalBrowser {
                UIApplication.shared.open(url)
            } else {
                // tell the app that we want to show the Safari VC
                self.showSafari = true
            }

        }, label: label)
            // summon the Safari sheet
            .sheet(isPresented: $showSafari) { SafariView(url: self.url) }
    }
}

/// A SFSafariViewController view that can be used in SwiftUI
struct SafariView: UIViewControllerRepresentable {
    let url: URL

    func makeUIViewController(context: UIViewControllerRepresentableContext<SafariView>) -> SFSafariViewController {
        return SFSafariViewController(url: url)
    }

    func updateUIViewController(_ uiViewController: SFSafariViewController,
                                context: UIViewControllerRepresentableContext<SafariView>) {
    }
}

struct WebButton_Previews: PreviewProvider {
    static var previews: some View {
        WebButton(url: URL(string: "https://www.google.com")!) { Text("Click me!") }
        .previewLayout(.fixed(width: 300, height: 100))
    }
}

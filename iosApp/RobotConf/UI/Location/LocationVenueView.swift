//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import URLImage
import MapKit
import shared

struct LocationVenueView: View {
    @StateObject private var viewModel: LocationVenueViewModel
    var kind: LocationVenueViewModel.VenueKind = .conference

    init(kind: LocationVenueViewModel.VenueKind) {
        switch kind {
            case .conference:
                self._viewModel = StateObject(wrappedValue: ConferenceVenueViewModel())
            case .party:
                self._viewModel = StateObject(wrappedValue: AfterPartyVenueViewModel())
        }

    }

    var body: some View {

            ScrollView {
                viewModel.content.map { content in
                VStack(alignment: .center, spacing: 16) {
                    URLImage(URL(string: content.imageUrl)!) { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    }.frame(maxHeight: 220)

                    Text(content.address)
                        .bold()
                        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 8)

                    // Set the width to 320 because it is the smallest width of the iPhones. This is a really dirty hack
                    // but using a geometry destroys the UI.
                    AttributedLabel(attributedText: content.detail.asHtmlAttributedString, width: 320)
                        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 8)

                    Button(stringResource(MR.strings().locations)) {
                        
                    }.padding(.vertical, 8)
                        .padding(.horizontal, 16)
                        .foregroundColor(Color.primary)
                        .background(Color.gray)
                        .cornerRadius(8)

                    Spacer()
                }.navigationBarTitle(Text(content.name), displayMode: .inline)
            }

            .task {
                await viewModel.activate()
            }
        }
    }
}

#if DEBUG
struct LocationVenueView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return LocationVenueView(kind: .conference)
    }
}
#endif


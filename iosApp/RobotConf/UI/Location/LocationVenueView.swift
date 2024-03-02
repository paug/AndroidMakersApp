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
                self._viewModel = StateObject(wrappedValue: ConferenceVenueViewModel(
                    getConferenceVenueUc: model.getConferenceVenueUC
                ))
            case .party:
                self._viewModel = StateObject(wrappedValue: AfterPartyVenueViewModel(
                    getAfterpartyUc: model.getAfterpartyVenueUC
                ))
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
                        if let coordinates = content.clCoordinates {
                            let placemark = MKPlacemark(coordinate: coordinates)
                            let mapItem = MKMapItem(placemark: placemark)
                            mapItem.name = content.name
                            mapItem.openInMaps()
                        } else {
                            let geoCoder = CLGeocoder()
                            geoCoder.geocodeAddressString(content.address) { (placemarks, error) in
                                guard let placemark = placemarks?.first else { return }
                                let mapItem = MKMapItem(placemark: MKPlacemark(placemark: placemark))
                                mapItem.name = content.name
                                mapItem.openInMaps()
                            }
                        }
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

extension LocationVenueViewModel.Content {
    var clCoordinates: CLLocationCoordinate2D? {
        get {
            let coords = coordinates?.split(separator: ",") ?? []
            let formatter = NumberFormatter()
            formatter.locale = Locale(identifier: "en_US")
            formatter.numberStyle = .decimal
            if coords.count == 2,
               let latitude = formatter.number(from: String(coords[0]))?.doubleValue,
               let longitude = formatter.number(from: String(coords[1]))?.doubleValue {
                return CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
            } else {
                return nil
            }
        }
    }
}

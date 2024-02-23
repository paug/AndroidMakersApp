//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI

struct AgendaCellView: View {
    var session: AgendaDayListViewModel.Content.Session
    var viewModel: AgendaDayListViewModel

    var durationFormatter: DateComponentsFormatter = {
        let formatter = DateComponentsFormatter()
        formatter.unitsStyle = .full
        formatter.allowedUnits = [.hour, .minute]
        return formatter
    }()

    var body: some View {
        HStack(alignment: .top) {
            VStack(alignment: .leading) {
                Text(session.title)
                    .foregroundColor(.blue)
                    .font(.headline)
                    .padding(.bottom, 4)
                Spacer()
                // swiftlint:disable:next line_length
                Text([
                    durationFormatter.string(from: session.duration)!,
                    session.room,
                    session.language?.flagDescription]
                    .compactMap { $0 }.joined(separator: " / "))
                    .font(.footnote)
                Text(session.speakers.map { $0.name }.joined(separator: ", "))
                    .font(.footnote)
            }
            Spacer()
            VStack {
                if session.isATalk {
                    Image(systemName: viewModel.favoriteSessions.contains(session.uid) ? "star.fill" : "star")
                        .foregroundColor(.yellow)
                        .padding(8)
                        .onTapGesture { self.viewModel.toggleFavorite(ofSession: self.session) }
                }
                if session.state != .none {
                    Spacer()
                    Text(session.state.localizedDescription)
                        .font(.footnote)
                        .bold()
                }

            }
        }.padding(.vertical, 4)
    }
}

extension AgendaDayListViewModel.Content.Session.State {
    var localizedDescription: String {
        switch self {
        case .current:  return L10n.Agenda.Detail.State.current
        case .isComing: return L10n.Agenda.Detail.State.isComing
        case .none:     return ""
        }
    }
}

#if DEBUG
struct AgendaCellView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return Group {
            AgendaCellView(session: AgendaDayListViewModel.Content.Session(
                uid: "1",
                title: "The infinite loop", duration: 25 * 60,
                speakers: [Speaker(
                    name: "Toto",
                    photoUrl: URL(string: "https://apod.nasa.gov/apod/image/1907/SpotlessSunIss_Colacurcio_2048.jpg")!,
                    company: "", description: "")],
                room: "Room 2.04", language: .french, state: .current),
                           viewModel: AgendaDayListViewModel())
        }
        .previewLayout(.fixed(width: 300, height: 100))
    }
}
#endif

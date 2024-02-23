//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import SwiftUI
import Combine

struct AgendaDayListView: View {
    @StateObject private var viewModel = AgendaDayListViewModel()
    @State private var favOnly = false
    @State private var highlightedSession = ""

    var sectionTimeFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()

    var sectionDateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "EEEE"
        return formatter
    }()

    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.content.sections, id: \.self) { section in
                    if section.isNewDay {
                        Text("\(self.sectionDateFormatter.string(from: section.date))")
                            .font(.title)
                            .bold()
                            .padding(.horizontal, -8)
                            .listRowBackground(Color.clear)
                    }
                    Section {
                        ForEach(self.favOnly ?
                                    section.sessions.filter({ self.viewModel.favoriteSessions.contains($0.uid) }) :
                                    section.sessions,
                                id: \.self) { session in
                                NavigationLink(destination: AgendaDetailView(sessionId: session.uid)
                                    .onAppear {
                                        // dirty hack to be able to highlight the cell that has just been taped
                                        highlightedSession = session.uid
                                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                                            highlightedSession = ""
                                        }
                                    }) {
                                        AgendaCellView(session: session, viewModel: self.viewModel)
                                    }
                                    .listRowBackground(
                                        highlightedSession == session.uid ?
                                            Color(UIColor.systemGray4) :
                                            session.state != .none ?
                                                Color(Asset.Colors.currentTalk.color) :
                                                nil) // default color
                        }
                    } header: {
                        Text("\(self.sectionDateFormatter.string(from: section.date)), " +
                             "\(self.sectionTimeFormatter.string(from: section.date))")
                    }
                }
            }
            .listStyle(.insetGrouped)
            .navigationBarTitle(Text(L10n.Agenda.navTitle), displayMode: .large)
            .navigationBarItems(trailing:
                Button(action: { self.favOnly.toggle() }) {
                    Image(systemName: favOnly ? "star.fill" : "star").padding()
                }
            )
                .onAppear { self.viewModel.viewAppeared() }
                .onDisappear { self.viewModel.viewDisappeared() }
            // Display the first session in the detail view to avoid having a white screen
            // (this will be only used when the master and detail are visible together i.e. on ipads)
            if viewModel.content.sections.first?.sessions.first?.uid != nil {
                AgendaDetailView(sessionId: viewModel.content.sections.first!.sessions.first!.uid)
            }
        }
        .navigationViewStyle(DoubleColumnNavigationViewStyle())
    }
}

#if DEBUG
struct AgendaDayListView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return AgendaDayListView()
    }
}
#endif

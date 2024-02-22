//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI

struct AgendaRoomListView: View {
    @StateObject private var viewModel = AgendaRoomListViewModel()
    @State private var favOnly = false

    var body: some View {
        NavigationView {
            ZStack {
                Rectangle()
                    .foregroundColor(Color(UIColor.systemGroupedBackground))
                    .edgesIgnoringSafeArea(.all)
                if !viewModel.content.rooms.isEmpty && !viewModel.content.hours.isEmpty {
                    NonEmptyContentView(
                        content: favOnly ?
                            viewModel.content.keepSessions(viewModel.favoriteSessions) : viewModel.content,
                        days: viewModel.days,
                        selectedDay: $viewModel.selectedDay)
                    
                } else {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                }
            }
            .onAppear { self.viewModel.viewAppeared() }
            .onDisappear { self.viewModel.viewDisappeared() }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .navigationBarTitle(Text(L10n.Agenda.navTitle), displayMode: .inline)
            .navigationBarItems(
                trailing:  HStack {
                    Button(action: { favOnly.toggle() }) {
                        Image(systemName: favOnly ? "star.fill" : "star").padding()
                    }
                }
            )
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

struct NonEmptyContentView: View {
    let content: AgendaRoomListViewModel.Content
    let days: [Date]
    @Binding var selectedDay: Date

    private let roomsHeight = CGFloat(30)
    private let hourWidth = CGFloat(70)
    private let hourHeight = CGFloat(150)
    private let sessionInset = CGFloat(4)
    private let sessionHalfInset = CGFloat(2)

    private let timeFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()

    private let dayFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .full
        formatter.timeStyle = .none
        return formatter
    }()

    var body: some View {
        GeometryReader { geometry in
            let screenWidth = geometry.size.width
            let columnWidth = (screenWidth - hourWidth) / CGFloat(content.rooms.count)
            // -1 because the last hour is just a end time, there won't be any sessions in it so no need for a full
            // height
            let fullHeight = roomsHeight + hourHeight * CGFloat(content.hours.count - 1) + 20
            VStack(spacing: 0) {
                Picker("", selection: $selectedDay) {
                    ForEach(days, id: \.self) {
                        Text(dayFormatter.string(from: $0)).tag($0)
                    }
                }
                .pickerStyle(.segmented)
                ZStack(alignment: .topLeading) {
                    ForEach(0...(content.rooms.count+1), id: \.self) { index in
                        HStack {
                            Divider()
                                .frame(height: roomsHeight)
                        }.offset(x: hourWidth + columnWidth * CGFloat(index) - 0.25)
                    }
                    VStack(spacing: 0) {
                        HStack(spacing: 0) {
                            Spacer()
                                .frame(width: hourWidth)
                            //                        Divider()
                            //                            .frame(height: roomsHeight)
                            ForEach(Array(content.rooms.enumerated()), id: \.element) { index, room in
                                Text(room)
                                    .font(.subheadline)
                                    .frame(width: columnWidth - 0.5)
                                //                            Divider()
                                //                                .frame(height: roomsHeight)
                            }
                        }.frame(height: roomsHeight)
                        ScrollView {
                            ZStack(alignment: .topLeading) {
                                ForEach(Array(content.hours.enumerated()), id: \.element) { index, hour in
                                    Text(timeFormatter.string(from: hour))
                                        .font(.subheadline)
                                        .offset(y: hourHeight * CGFloat(index) + 6)
                                        .frame(width: hourWidth)
                                    Divider()
                                        .frame(width: screenWidth)
                                        .offset(y: hourHeight * CGFloat(index))
                                }
                                ForEach(content.sessions, id: \.self) { session in
                                    SessionView(
                                        session: session.data, size: CGSize(
                                            width: columnWidth * CGFloat(session.rooms.distance + 1) - sessionInset,
                                            height: hourHeight * CGFloat(session.duration) - sessionInset))
                                    .offset(x: hourWidth + columnWidth * CGFloat(session.rooms.lowerBound) + sessionHalfInset)
                                    .offset(y: hourHeight * CGFloat(session.startDateIdx) + sessionHalfInset)
                                }
                            }
                            .frame(width: geometry.size.width,
                                   height: fullHeight,
                                   alignment: .topLeading)
                        }
                    }
                }
            }
        }
    }
}

private struct SessionView: View {
    let session: Session
    let size: CGSize
    @State private var isActive = false

    var body: some View {
        NavigationLink(destination: AgendaDetailView(sessionId: session.uid)) {
            VStack(alignment: .leading) {
                Text(session.title)
                    .foregroundColor(Color(UIColor.label))
                    .bold()
                    .font(.subheadline)
                    .multilineTextAlignment(.leading)
                Spacer()
                HStack(alignment: .bottom) {
                    Text(session.speakers.map { $0.name }.joined(separator: ", "))
                        .foregroundColor(Color(UIColor.label))
                        .font(.subheadline)
                        .multilineTextAlignment(.leading)
                    Spacer()
                    if session.isATalk {
                        Text(session.language.flagDescription)
                            .font(.caption2)
                    }
                }
            }
            .padding(6)
            .frame(width: size.width, height: size.height, alignment: .topLeading)
            .background(Color(UIColor.secondarySystemGroupedBackground))
            .cornerRadius(8)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(Color(Asset.Colors.amBlue.color), lineWidth: 1)
            )
        }
    }
}

struct AgendaRoomListView_Previews: PreviewProvider {
    static var previews: some View {
        AgendaRoomListView()
    }
}

extension Color {
    static var random: Color {
        return .init(red: .random(in: 0...1),
                     green: .random(in: 0...1),
                     blue: .random(in: 0...1))
    }
}

extension ClosedRange where Bound == Int {
    var distance: Int { upperBound - lowerBound }
}

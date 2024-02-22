//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Combine

class AgendaRoomListViewModel: ObservableObject {
    struct Content {
        struct SessionContent: Hashable {
            let data: Session
            let startDateIdx: Int
            let duration: Int
            let rooms: ClosedRange<Int>
        }
        let rooms: [String]
        let hours: [Date]
        let sessions: [SessionContent]

        func keepSessions(_ sessionUids: Set<String>) -> Self {
            let filteredSessions = sessions.filter { sessionUids.contains($0.data.uid) }
            return .init(rooms: rooms, hours: hours, sessions: filteredSessions)
        }
    }

    @Published private(set) var content = Content(rooms: [], hours: [], sessions: [])
    @Published private(set) var days = [Date]()
    @Published var favoriteSessions: Set<String> = []
    @Published var selectedDay = Date.distantFuture

    private var sessionRepo: SessionRepository
    private var isDisplayed = false
    private var disposables = Set<AnyCancellable>()

    init(sessionRepo: SessionRepository = model.sessionRepository) {
        self.sessionRepo = sessionRepo

        sessionRepo.getFavoriteSessions().sink { [unowned self] in
            // only update when view is displayed otherwise it will redisplay the list when the favorite state changes
            if self.isDisplayed {
                self.favoriteSessions = $0
            }
        }.store(in: &disposables)

        sessionRepo.getSessions().sink { [unowned self] sessions in
            var allDates = Set<DateComponents>()
            let calendar = Calendar.current
            sessions.forEach { allDates.insert(calendar.dateComponents([.day, .month, .year], from: $0.startTime)) }
            let days = allDates.sorted {
                if $0.month == $1.month {
                    return $0.day ?? 0 < $1.day ?? 0
                }
                return $0.month ?? 0 < $1.month ?? 0
            }
            self.days = days.compactMap { calendar.date(from: $0) }
        }.store(in: &disposables)

        $days
            .removeDuplicates()
            .sink { [unowned self] days in
                guard let firstDay = days.first else { return }
                selectedDay = firstDay
            }.store(in: &disposables)

        Publishers.CombineLatest(
            sessionRepo.getSessions(),
            $selectedDay)
        .sink { [unowned self] in
            sessionsChanged(sessions: $0, selectedDay: $1)
        }.store(in: &disposables)
    }

    func viewAppeared() {
        isDisplayed = true

        // recompute sessions in case status have changed
        sessionsChanged(sessions: sessionRepo.sessions, selectedDay: selectedDay)
        favoriteSessions = sessionRepo.favoriteSessions
    }

    func viewDisappeared() {
        isDisplayed = false
    }

    private func sessionsChanged(sessions: [Session], selectedDay: Date) {
        let calendar = Calendar.current
        let sessionsToConsider = sessions.filter {
            calendar.isDate($0.startTime, inSameDayAs: selectedDay)
        }

        var allRooms = Set<Room>()
        sessionsToConsider.forEach {
            // only consider rooms of the talks as the non talks are expanded on all rooms
            if $0.isATalk {
                allRooms.insert($0.room)
            }
        }
        let rooms = allRooms.sorted { $0.index < $1.index }
        let roomsIndexes = Dictionary(uniqueKeysWithValues: rooms.map { ($0.uid, Int(rooms.firstIndex(of: $0)!)) })

        var allTimes = Set<Date>()
        sessionsToConsider.forEach {
            allTimes.insert($0.startTime)
            allTimes.insert($0.startTime.addingTimeInterval($0.duration))
        }
        let times = allTimes.sorted()
        // filter small gaps (less than 10 minutes) in the schedule to only display the latest date
        var filteredTimes = [Date]()
        for (index, time) in times.enumerated() {
            guard index + 1 < times.count else {
                filteredTimes.append(time)
                continue
            }
            let nextTime = times[index + 1]
            if nextTime.timeIntervalSince(time) > 10 * 60 {
                filteredTimes.append(time)
            }
        }
        let timesIndex = Dictionary(uniqueKeysWithValues: filteredTimes.map {
            ($0, Int(filteredTimes.firstIndex(of: $0)!))
        })

        var sessions = [Content.SessionContent]()
        let roomMinIdx = roomsIndexes.values.min() ?? 0
        let roomMaxIdx = roomsIndexes.values.max() ?? 0
        // sort by non talks first in order to have the breaks below the talks
        for session in sessionsToConsider.sorted(by: { !$0.isATalk && $1.isATalk }) {
            let endTime = session.startTime + session.duration
            guard let startDateIdx = timesIndex[session.startTime],
                  let roomIdx = roomsIndexes[session.room.uid],
                  let nearestEndTimeIdx = filteredTimes.firstIndex(where: { $0 >= endTime }) else {
                continue
            }

            let contentSession = Content.SessionContent(
                data: session,
                startDateIdx: startDateIdx,
                duration: nearestEndTimeIdx - startDateIdx,
                rooms: session.isATalk ? roomIdx...roomIdx : roomMinIdx...roomMaxIdx)
            sessions.append(contentSession)
        }
        content = Content(rooms: rooms.map { $0.name }, hours: filteredTimes, sessions: sessions)
    }
}

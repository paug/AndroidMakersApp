//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import Apollo
import Combine
import FirebaseCrashlytics

/// Object that provides talks from Firestore
class GraphQLTalksProvider {
    var talksPublisher = PassthroughSubject<[SessionData], Error>()

    private let apolloClient: ApolloClient

    init(apolloClient: ApolloClient) {
        self.apolloClient = apolloClient
        apolloClient.fetch(query: GraphQLData.GetTalksQuery(),
                           cachePolicy: .returnCacheDataAndFetch) { [weak self] result in
            guard let self = self else { return }
            do {
                let fetchedResult = try result.get()
                guard let gqlSessions = fetchedResult.data?.sessions else {
                    print("Error getting graphQL talks: \(fetchedResult.errors ?? [])")
                    self.talksPublisher.send(completion: .failure(
                        fetchedResult.errors?.first ?? NSError(domain: "Empty data", code: 1)))
                    return
                }
                var roomsByIds = [String: (GraphQLData.GetTalksQuery.Data.Room, Int)]()
                if let gqlRooms = fetchedResult.data?.rooms {
                    var idx = 0
                    for gqlRoom in gqlRooms {
                        roomsByIds[gqlRoom.id] = (gqlRoom, idx)
                        idx += 1
                    }
                }
                var sessionsData = [SessionData]()
                let dateFormatter = DateFormatter()
                dateFormatter.locale = Locale(identifier: "en_US_POSIX") // TODO Djavan: Check si c'est correct
                dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm"
                for gqlSession in gqlSessions.nodes {
                    guard let startDate = dateFormatter.date(from: gqlSession.startsAt) else {
                        print("Date \(gqlSession.startsAt) not recognized")
                        continue
                    }
                    guard let endDate = dateFormatter.date(from: gqlSession.endsAt) else {
                        print("Date \(gqlSession.endsAt) not recognized")
                        continue
                    }
                    let sessionData = SessionData(
                        uid: gqlSession.id,
                        title: gqlSession.title,
                        description: gqlSession.description ?? "",
                        speakers: gqlSession.speakers.map {
                            SessionData.Speaker(country: $0.city, name: $0.name, photoUrl: $0.photoUrl, bio: $0.bio,
                                                shortBio: $0.bio, // TODO: Djavan short bio
                                                company: $0.company)
                        },
                        tags: gqlSession.tags,
                        startTime: startDate,
                        endTime: endDate,
                        room: gqlSession.rooms.first.map {
                            let (room, idx) = roomsByIds[$0.id] ?? (nil, nil)
                            return SessionData.Room(id: $0.id, name: room?.name ?? $0.name, index: idx ?? 1000)
                        } ?? .init(id: "unknown", name: "ROOOM", index: 1000),
                        language: gqlSession.language,
                        complexity: gqlSession.complexity,
                        questionUrl: nil,
                        youtubeUrl: nil,
                        slidesUrl: nil)
                    sessionsData.append(sessionData)
                }
                self.talksPublisher.send(sessionsData)
            } catch let error {
                Crashlytics.crashlytics().record(error: error)
                self.talksPublisher.send(completion: .failure(error))
            }
        }
    }
}

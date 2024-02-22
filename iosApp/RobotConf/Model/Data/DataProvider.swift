//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import CoreLocation
import UIKit
import Apollo

protocol DataProviderProtocol {
    var sessionsPublisher: AnyPublisher<[SessionData], Error> { get }
    var confVenuePublisher: AnyPublisher<VenueData, Error> { get }
    var partyVenuePublisher: AnyPublisher<VenueData, Error> { get }
    var partnersPublisher: AnyPublisher<[PartnersByCategoryData], Error> { get }
    var votesPublisher: AnyPublisher<[String: TalkFeedback], Error> { get }

    func vote(_ proposition: TalkFeedback.Proposition, for talkId: String)
    func removeVote(_ proposition: TalkFeedback.Proposition, for talkId: String)
}

/// Object that transforms and provides model data from server data
class DataProvider {
    enum ProviderType {
        case firestore
        case graphQL
        #if DEBUG
        case json
        #endif
    }

    var sessionsPublisher = PassthroughSubject<[Session], Error>()
    var confVenuePublisher = PassthroughSubject<Venue, Error>()
    var partyVenuePublisher = PassthroughSubject<Venue, Error>()
    var partnerPublisher = PassthroughSubject<[PartnerCategory], Error>()
    let votesPublisher: AnyPublisher<[String: TalkFeedback], Error>

    private var cancellables: Set<AnyCancellable> = []

    let proxyDataProvider: DataProviderProtocol

    init(desiredProviderType: ProviderType = .graphQL) {
        proxyDataProvider = GraphQLDataProvider()

        votesPublisher = proxyDataProvider.votesPublisher

        computeSessions()
        computeVenues()
        computePartners()
    }

    func vote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        proxyDataProvider.vote(proposition, for: talkId)
    }

    func removeVote(_ proposition: TalkFeedback.Proposition, for talkId: String) {
        proxyDataProvider.removeVote(proposition, for: talkId)
    }

    private func computeSessions() {
        proxyDataProvider.sessionsPublisher.sink(receiveCompletion: { error in
            print("Error computing talks \(error)")
        }) { [unowned self] sessionsData in
            var sessions = [Session]()
            for sessionData in sessionsData {
                let speakers: [Speaker] = sessionData.speakers.compactMap {
                    guard let name = $0.name else { return nil }
                    return Speaker(
                        name: name,
                        // can force unwrap URL because URL(string: "") is returning a non nil url
                        photoUrl: $0.photoUrl.map { URL(string: $0) ?? URL(string: "")! } ?? URL(string: "")!,
                        company: $0.company ?? "", description: $0.bio ?? "")
                }
                let talk = Session(
                    uid: sessionData.uid,
                    title: sessionData.title,
                    description: sessionData.description,
                    duration: sessionData.endTime.timeIntervalSince(sessionData.startTime),
                    speakers: speakers, tags: sessionData.tags, startTime: sessionData.startTime,
                    room: Room(uid: sessionData.room.id, name: sessionData.room.name, index: sessionData.room.index),
                    language: Language(from: sessionData.language),
                    complexity: Session.Complexity(from: sessionData.complexity),
                    questionUrl: URL(string: sessionData.questionUrl),
                    youtubeUrl: URL(string: sessionData.youtubeUrl),
                    slidesUrl: URL(string: sessionData.slidesUrl))
                sessions.append(talk)
            }
            self.sessionsPublisher.send(sessions)
        }.store(in: &cancellables)
    }

    private func computeVenues() {
        proxyDataProvider.confVenuePublisher
            .sink(receiveCompletion: { error in
                print("Error computing conf venue \(error)")
            }) { [unowned self] venue in
                guard let confVenue = Venue(from: venue) else {
                    // self.confVenuePublisher.send(completion: .failure())
                    return
                }

                self.confVenuePublisher.send(confVenue)
        }.store(in: &cancellables)

        proxyDataProvider.partyVenuePublisher
            .sink(receiveCompletion: { error in
                print("Error computing party venue \(error)")
            }) { [unowned self] venue in
                guard let partyVenue = Venue(from: venue) else {
                    // self.partyVenuePublisher.send(completion: .failure())
                    return
                }

                self.partyVenuePublisher.send(partyVenue)
        }.store(in: &cancellables)
    }

    private func computePartners() {
        proxyDataProvider.partnersPublisher
            .sink(receiveCompletion: { error in
                print("Error computing partners \(error)")
            }) { [unowned self] partnerCategories in
                self.partnerPublisher.send([PartnerCategory](from: partnerCategories))
        }.store(in: &cancellables)
    }
}

private extension Language {
    init(from str: String?) {
        guard let str = str else {
            self = .all
            return
        }
        var language: Language = []
        if str.localizedCaseInsensitiveContains("english") {
            language.formUnion(.english)
        }
        if str.localizedCaseInsensitiveContains("french") {
            language.formUnion(.french)
        }
        self = language
    }
}

private extension Session.Complexity {
    init?(from str: String?) {
        switch str {
        case "Beginner": self = .beginner
        case "Intermediate": self = .intermediate
        case "Expert": self = .expert
        default: return nil
        }
    }
}

private extension Venue {
    init?(from venue: VenueData) {
        let coords = venue.coordinates?.split(separator: ",") ?? []
        let formatter = NumberFormatter()
        formatter.locale = Locale(identifier: "en_US")
        formatter.numberStyle = .decimal
        let coordinates: CLLocationCoordinate2D?
        if coords.count == 2,
            let latitude = formatter.number(from: String(coords[0]))?.doubleValue,
            let longitude = formatter.number(from: String(coords[1]))?.doubleValue {
            coordinates = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        } else {
            coordinates = nil
        }
        self = Venue(name: venue.name, description: venue.description, descriptionFr: venue.descriptionFr,
                     address: venue.address,
                     coordinates: coordinates,
                     imageUrl: venue.imageUrl)
    }
}

private extension Array where Element == PartnerCategory {
    init(from partnersByCategories: [PartnersByCategoryData]) {
        self = partnersByCategories
            .sorted { $0.category.order < $1.category.order }
            .compactMap { partnersByCategory in
                let partners = partnersByCategory.partners.compactMap { Partner(from: $0) }
                guard !partners.isEmpty else { return nil }
                return PartnerCategory(categoryName: partnersByCategory.category.name, partners: partners)
        }
    }
}

private extension Partner {
    init?(from partner: PartnerData) {
        guard let logoUrl = URL(string: partner.logoUrl) else { return nil }
        self.init(name: partner.name, logoUrl: logoUrl, url: URL(string: partner.url))
    }
}

private extension TalkFeedback.Proposition {
    init?(from voteItem: VoteConfigData.VoteItem, language: String) {
        guard voteItem.type == "boolean" else { return nil }
        self = TalkFeedback.Proposition(uid: voteItem.id, text: voteItem.languages?[language] ?? voteItem.name)
    }
}

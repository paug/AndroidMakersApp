//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import FirebaseFirestore
import Combine
import FirebaseCrashlytics

/// Object that provides partners from Firestore
class FirestorePartnersProvider: PartnersProvider {
    var partnersPublisher = CurrentValueSubject<[PartnersByCategoryData], Error>([])

    private var categoriesByDocPublisher = PassthroughSubject<[String: PartnerCategoryData], Error>()
    private var categoriesByDocCancellable: AnyCancellable?

    init(database: Firestore) {
        // since we cannot get both a document information and a collection, we need to first get the document (category
        // information) and then query the collection stored in this document (the partners linked to that category).
        database.collection("partners").getDocuments { [weak self] (querySnapshot, err) in
            guard let self = self else { return }
            if let err = err {
                print("Error getting partners documents: \(err)")
                self.categoriesByDocPublisher.send(completion: .failure(err))
            } else {
                do {
                    var categoriesByDoc = [String: PartnerCategoryData]()
                    for document in querySnapshot!.documents {
                        categoriesByDoc[document.documentID] = try document.decoded()
                    }
                    self.categoriesByDocPublisher.send(categoriesByDoc)
                } catch let error {
                    Crashlytics.crashlytics().record(error: error)
                    self.categoriesByDocPublisher.send(completion: .failure(error))
                }
            }
        }

        categoriesByDocCancellable = categoriesByDocPublisher.sink { [unowned self] completion in
            self.partnersPublisher.send(completion: completion)
        } receiveValue: { categoryByDoc in
            for (documentId, category) in categoryByDoc {
                database.collection("partners/\(documentId)/items").getDocuments { [weak self] (querySnapshot, err) in
                    guard let self = self else { return }
                    if let err = err {
                        print("Error getting partners documents for category \(category.name): \(err)")
                    } else {
                        do {
                            var partners = [PartnerData]()
                            for document in querySnapshot!.documents {
                                partners.append(try document.decoded())
                            }
                            var partnersByCategory = self.partnersPublisher.value
                            partnersByCategory.append(PartnersByCategoryData(category: category, partners: partners))
                            self.partnersPublisher.send(partnersByCategory)
                        } catch let error {
                            Crashlytics.crashlytics().record(error: error)
                        }
                    }
                }
            }
        }

    }
}

//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation

extension URL {

    func appendingQueryItem(name: String, value: String?) -> URL? {
        guard var urlComponents = URLComponents(string: absoluteString) else { return nil }

        // Create array of existing query items
        var queryItems: [URLQueryItem] = urlComponents.queryItems ??  []

        // Create query item
        let queryItem = URLQueryItem(name: name, value: value)

        // Append the new query item in the existing query items array
        queryItems.append(queryItem)

        // Append updated query items array in the url component object
        urlComponents.queryItems = queryItems

        // Returns the url from new url components
        return urlComponents.url
    }
}

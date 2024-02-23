// @generated
// This file was automatically generated and should not be edited.

@_exported import ApolloAPI

public extension GraphQLData {
  class GetVenuesQuery: GraphQLQuery {
    public static let operationName: String = "GetVenues"
    public static let document: ApolloAPI.DocumentType = .notPersisted(
      definition: .init(
        #"""
        query GetVenues {
          venues {
            __typename
            id
            name
            address
            coordinates
            description
            descriptionFr: description(language: "fr")
            imageUrl
          }
        }
        """#
      ))

    public init() {}

    public struct Data: GraphQLData.SelectionSet {
      public let __data: DataDict
      public init(data: DataDict) { __data = data }

      public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Query }
      public static var __selections: [ApolloAPI.Selection] { [
        .field("venues", [Venue].self),
      ] }

      public var venues: [Venue] { __data["venues"] }

      /// Venue
      ///
      /// Parent Type: `Venue`
      public struct Venue: GraphQLData.SelectionSet {
        public let __data: DataDict
        public init(data: DataDict) { __data = data }

        public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Venue }
        public static var __selections: [ApolloAPI.Selection] { [
          .field("id", String.self),
          .field("name", String.self),
          .field("address", String?.self),
          .field("coordinates", String?.self),
          .field("description", String.self),
          .field("description", alias: "descriptionFr", String.self, arguments: ["language": "fr"]),
          .field("imageUrl", String?.self),
        ] }

        public var id: String { __data["id"] }
        public var name: String { __data["name"] }
        public var address: String? { __data["address"] }
        @available(*, deprecated, message: "use latitude and longitude instead")
        public var coordinates: String? { __data["coordinates"] }
        public var description: String { __data["description"] }
        public var descriptionFr: String { __data["descriptionFr"] }
        public var imageUrl: String? { __data["imageUrl"] }
      }
    }
  }

}
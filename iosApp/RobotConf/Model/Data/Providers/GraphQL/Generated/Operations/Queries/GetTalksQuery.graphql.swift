// @generated
// This file was automatically generated and should not be edited.

@_exported import ApolloAPI

public extension GraphQLData {
  class GetTalksQuery: GraphQLQuery {
    public static let operationName: String = "GetTalks"
    public static let document: ApolloAPI.DocumentType = .notPersisted(
      definition: .init(
        #"""
        query GetTalks {
          rooms {
            __typename
            capacity
            id
            name
          }
          sessions(first: 1000) {
            __typename
            nodes {
              __typename
              complexity
              description
              endsAt
              id
              language
              rooms {
                __typename
                capacity
                id
                name
              }
              shortDescription
              speakers {
                __typename
                id
                name
                photoUrl
                company
                bio
                city
              }
              startsAt
              tags
              title
              type
            }
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
        .field("rooms", [Room].self),
        .field("sessions", Sessions.self, arguments: ["first": 1000]),
      ] }

      public var rooms: [Room] { __data["rooms"] }
      public var sessions: Sessions { __data["sessions"] }

      /// Room
      ///
      /// Parent Type: `Room`
      public struct Room: GraphQLData.SelectionSet {
        public let __data: DataDict
        public init(data: DataDict) { __data = data }

        public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Room }
        public static var __selections: [ApolloAPI.Selection] { [
          .field("capacity", Int?.self),
          .field("id", String.self),
          .field("name", String.self),
        ] }

        public var capacity: Int? { __data["capacity"] }
        public var id: String { __data["id"] }
        public var name: String { __data["name"] }
      }

      /// Sessions
      ///
      /// Parent Type: `SessionConnection`
      public struct Sessions: GraphQLData.SelectionSet {
        public let __data: DataDict
        public init(data: DataDict) { __data = data }

        public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.SessionConnection }
        public static var __selections: [ApolloAPI.Selection] { [
          .field("nodes", [Node].self),
        ] }

        public var nodes: [Node] { __data["nodes"] }

        /// Sessions.Node
        ///
        /// Parent Type: `Session`
        public struct Node: GraphQLData.SelectionSet {
          public let __data: DataDict
          public init(data: DataDict) { __data = data }

          public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Session }
          public static var __selections: [ApolloAPI.Selection] { [
            .field("complexity", String?.self),
            .field("description", String?.self),
            .field("endsAt", GraphQLData.LocalDateTime.self),
            .field("id", String.self),
            .field("language", String?.self),
            .field("rooms", [Room].self),
            .field("shortDescription", String?.self),
            .field("speakers", [Speaker].self),
            .field("startsAt", GraphQLData.LocalDateTime.self),
            .field("tags", [String].self),
            .field("title", String.self),
            .field("type", String.self),
          ] }

          public var complexity: String? { __data["complexity"] }
          public var description: String? { __data["description"] }
          public var endsAt: GraphQLData.LocalDateTime { __data["endsAt"] }
          public var id: String { __data["id"] }
          /// An [IETF language code](https://en.wikipedia.org/wiki/IETF_language_tag) like en-US
          public var language: String? { __data["language"] }
          public var rooms: [Room] { __data["rooms"] }
          /// A shorter version of description for use when real estate is scarce like watches for an example.
          /// This field might have the same value as description if a shortDescription is not available
          public var shortDescription: String? { __data["shortDescription"] }
          public var speakers: [Speaker] { __data["speakers"] }
          public var startsAt: GraphQLData.LocalDateTime { __data["startsAt"] }
          public var tags: [String] { __data["tags"] }
          public var title: String { __data["title"] }
          /// One of "break", "lunch", "party", "keynote", "talk" or any other conference-specific format
          public var type: String { __data["type"] }

          /// Sessions.Node.Room
          ///
          /// Parent Type: `Room`
          public struct Room: GraphQLData.SelectionSet {
            public let __data: DataDict
            public init(data: DataDict) { __data = data }

            public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Room }
            public static var __selections: [ApolloAPI.Selection] { [
              .field("capacity", Int?.self),
              .field("id", String.self),
              .field("name", String.self),
            ] }

            public var capacity: Int? { __data["capacity"] }
            public var id: String { __data["id"] }
            public var name: String { __data["name"] }
          }

          /// Sessions.Node.Speaker
          ///
          /// Parent Type: `Speaker`
          public struct Speaker: GraphQLData.SelectionSet {
            public let __data: DataDict
            public init(data: DataDict) { __data = data }

            public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Speaker }
            public static var __selections: [ApolloAPI.Selection] { [
              .field("id", String.self),
              .field("name", String.self),
              .field("photoUrl", String?.self),
              .field("company", String?.self),
              .field("bio", String?.self),
              .field("city", String?.self),
            ] }

            public var id: String { __data["id"] }
            public var name: String { __data["name"] }
            public var photoUrl: String? { __data["photoUrl"] }
            public var company: String? { __data["company"] }
            public var bio: String? { __data["bio"] }
            public var city: String? { __data["city"] }
          }
        }
      }
    }
  }

}
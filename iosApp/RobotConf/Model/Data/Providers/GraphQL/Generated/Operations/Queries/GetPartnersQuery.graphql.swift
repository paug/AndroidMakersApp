// @generated
// This file was automatically generated and should not be edited.

@_exported import ApolloAPI

public extension GraphQLData {
  class GetPartnersQuery: GraphQLQuery {
    public static let operationName: String = "GetPartners"
    public static let document: ApolloAPI.DocumentType = .notPersisted(
      definition: .init(
        #"""
        query GetPartners {
          partnerGroups {
            __typename
            partners {
              __typename
              logoUrl
              name
              url
            }
            title
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
        .field("partnerGroups", [PartnerGroup].self),
      ] }

      public var partnerGroups: [PartnerGroup] { __data["partnerGroups"] }

      /// PartnerGroup
      ///
      /// Parent Type: `PartnerGroup`
      public struct PartnerGroup: GraphQLData.SelectionSet {
        public let __data: DataDict
        public init(data: DataDict) { __data = data }

        public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.PartnerGroup }
        public static var __selections: [ApolloAPI.Selection] { [
          .field("partners", [Partner].self),
          .field("title", String.self),
        ] }

        public var partners: [Partner] { __data["partners"] }
        public var title: String { __data["title"] }

        /// PartnerGroup.Partner
        ///
        /// Parent Type: `Partner`
        public struct Partner: GraphQLData.SelectionSet {
          public let __data: DataDict
          public init(data: DataDict) { __data = data }

          public static var __parentType: ApolloAPI.ParentType { GraphQLData.Objects.Partner }
          public static var __selections: [ApolloAPI.Selection] { [
            .field("logoUrl", String.self),
            .field("name", String.self),
            .field("url", String.self),
          ] }

          public var logoUrl: String { __data["logoUrl"] }
          public var name: String { __data["name"] }
          public var url: String { __data["url"] }
        }
      }
    }
  }

}
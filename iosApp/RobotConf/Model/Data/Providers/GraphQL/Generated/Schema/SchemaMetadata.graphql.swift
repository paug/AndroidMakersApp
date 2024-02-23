// @generated
// This file was automatically generated and should not be edited.

import ApolloAPI

public protocol GraphQLData_SelectionSet: ApolloAPI.SelectionSet & ApolloAPI.RootSelectionSet
where Schema == GraphQLData.SchemaMetadata {}

public protocol GraphQLData_InlineFragment: ApolloAPI.SelectionSet & ApolloAPI.InlineFragment
where Schema == GraphQLData.SchemaMetadata {}

public protocol GraphQLData_MutableSelectionSet: ApolloAPI.MutableRootSelectionSet
where Schema == GraphQLData.SchemaMetadata {}

public protocol GraphQLData_MutableInlineFragment: ApolloAPI.MutableSelectionSet & ApolloAPI.InlineFragment
where Schema == GraphQLData.SchemaMetadata {}

public extension GraphQLData {
  typealias ID = String

  typealias SelectionSet = GraphQLData_SelectionSet

  typealias InlineFragment = GraphQLData_InlineFragment

  typealias MutableSelectionSet = GraphQLData_MutableSelectionSet

  typealias MutableInlineFragment = GraphQLData_MutableInlineFragment

  enum SchemaMetadata: ApolloAPI.SchemaMetadata {
    public static let configuration: ApolloAPI.SchemaConfiguration.Type = SchemaConfiguration.self

    public static func objectType(forTypename typename: String) -> Object? {
      switch typename {
      case "Query": return GraphQLData.Objects.Query
      case "Venue": return GraphQLData.Objects.Venue
      case "Room": return GraphQLData.Objects.Room
      case "SessionConnection": return GraphQLData.Objects.SessionConnection
      case "Session": return GraphQLData.Objects.Session
      case "Speaker": return GraphQLData.Objects.Speaker
      case "PartnerGroup": return GraphQLData.Objects.PartnerGroup
      case "Partner": return GraphQLData.Objects.Partner
      default: return nil
      }
    }
  }

  enum Objects {}
  enum Interfaces {}
  enum Unions {}

}
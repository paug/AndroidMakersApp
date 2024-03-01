package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient

expect class ApolloClientBuilder {
  fun build(): ApolloClient
}

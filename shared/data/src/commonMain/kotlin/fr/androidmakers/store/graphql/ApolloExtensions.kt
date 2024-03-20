package fr.androidmakers.store.graphql

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.CacheMissException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot

internal fun <T : Operation.Data> Flow<ApolloResponse<T>>.ignoreCacheMisses(): Flow<ApolloResponse<T>> {
  return filterNot {
    // Ignore cache misses
    it.exception is CacheMissException
  }
}

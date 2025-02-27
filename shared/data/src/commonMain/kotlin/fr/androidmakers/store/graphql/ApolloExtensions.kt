package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.CacheMissException
import com.apollographql.apollo.exception.DefaultApolloException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow

internal fun <T : Operation.Data> Flow<ApolloResponse<T>>.ignoreCacheMisses(): Flow<ApolloResponse<T>> {
  return filterNot {
    // Ignore cache misses
    it.exception is CacheMissException
  }
}


internal fun <T : Operation.Data> ApolloCall<T>.cacheAndNetwork(): Flow<Result<T>> {
  return flow {
    var hasData = false
    var exception: ApolloException? = null
    fetchPolicy(FetchPolicy.CacheAndNetwork)
      .toFlow()
      .collect {
        val data = it.data
        if (data != null) {
          hasData = true
          emit(Result.success(data))
        } else {
          exception = it.exception
        }
      }
    if (!hasData) {
      emit(Result.failure(exception ?: DefaultApolloException("No data found")))
    }
  }
}

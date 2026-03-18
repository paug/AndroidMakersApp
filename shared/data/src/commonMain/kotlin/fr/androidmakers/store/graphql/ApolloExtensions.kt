package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.DefaultApolloException
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.fetchPolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T : Operation.Data> ApolloCall<T>.cacheAndNetwork(
  refresh: Boolean = false
): Flow<Result<T>> {
  return flow {
    var hasData = false
    var exception: ApolloException? = null
    fetchPolicy(if (refresh) FetchPolicy.NetworkFirst else FetchPolicy.CacheAndNetwork)
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

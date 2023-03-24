package fr.androidmakers.store.graphql

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain

fun GraphQLStore(context: Context): GraphQLStore {
  val cacheFactory = MemoryCacheFactory(20_000_000).chain(SqlNormalizedCacheFactory(context))
  val apolloClient = ApolloClient.Builder()
      .serverUrl("https://confetti-app.dev/graphql")
      .addHttpInterceptor(object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
          return chain.proceed(request.newBuilder().addHeader("conference", "androidmakers2023").build())
        }
      })
      .normalizedCache(cacheFactory)
      .build()
  return GraphQLStore(apolloClient)
}
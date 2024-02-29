package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain

actual class ApolloClientBuilder(private val token: String) {
  private val cacheFactory = MemoryCacheFactory(20_000_000).chain(SqlNormalizedCacheFactory())

  actual fun build(): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl("https://androidmakers-2023.ew.r.appspot.com/graphql")
        .addHttpInterceptor(object : HttpInterceptor {
          override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
            return chain.proceed(
                request.newBuilder()
                    .addHeader("conference", "androidmakers2023")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
          }
        }
        ).normalizedCache(cacheFactory)
        .build()
  }
}

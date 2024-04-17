package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import fr.androidmakers.domain.repo.UserRepository

fun ApolloClient(
  sqlNormalizedCacheFactory: SqlNormalizedCacheFactory,
  userRepository: UserRepository,
): ApolloClient {
  val memoryCacheFactory = MemoryCacheFactory(20_000_000).chain(sqlNormalizedCacheFactory)
  return ApolloClient.Builder()
      .serverUrl("https://androidmakers.fr/graphql")
      .addHttpInterceptor(object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
          return chain.proceed(
            request.newBuilder()
              .apply {
                /**
                 *
                 */
                val token = userRepository.getIdToken()
                if (token != null) {
                  addHeader("Authorization", "Bearer $token")
                }
              }
              .build()
          )
        }
      })
      .normalizedCache(memoryCacheFactory)
      .build()
}

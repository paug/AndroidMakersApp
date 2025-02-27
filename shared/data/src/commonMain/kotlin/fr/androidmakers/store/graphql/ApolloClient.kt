package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import fr.androidmakers.domain.repo.UserRepository

expect suspend fun getIdToken(userRepository: UserRepository): String?

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
                val token = getIdToken(userRepository)
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

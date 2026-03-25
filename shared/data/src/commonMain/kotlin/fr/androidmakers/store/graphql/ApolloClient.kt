package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.apollographql.apollo.network.ws.WebSocketNetworkTransport
import com.apollographql.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.cache.normalized.memory.MemoryCacheFactory
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.store.graphql.cache.Cache.cache

expect suspend fun getIdToken(userRepository: UserRepository): String?

fun ApolloClient(
  sqlNormalizedCacheFactory: NormalizedCacheFactory,
  userRepository: UserRepository,
): ApolloClient {
  return ApolloClient.Builder()
      .serverUrl("https://androidmakers.fr/graphql")
//      .serverUrl("http://10.1.3.174:8080/graphql")
      .addHttpInterceptor(object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
          return chain.proceed(
            request.newBuilder()
              .apply {
                val token = getIdToken(userRepository)
                if (token != null) {
                  addHeader("Authorization", "Bearer $token")
                }
              }
              .build()
          )
        }
      })
    .cache(MemoryCacheFactory(20_000_000).chain(sqlNormalizedCacheFactory))
      .build()
}

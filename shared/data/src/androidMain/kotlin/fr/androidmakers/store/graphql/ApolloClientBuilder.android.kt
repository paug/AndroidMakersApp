package fr.androidmakers.store.graphql

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

actual class ApolloClientBuilder(
    context: Context,
    private val url: String,
    private val conference: String
) {
  private val memoryCacheFactory = MemoryCacheFactory(20_000_000).chain(SqlNormalizedCacheFactory(context))
  actual fun build(): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl(url)
        .addHttpInterceptor(object : HttpInterceptor {
          override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
            return chain.proceed(
                request.newBuilder()
                    .addHeader("conference", conference)
                    .apply {
                      val token = Firebase.auth.currentUser?.getIdToken(false)?.result?.token
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
}

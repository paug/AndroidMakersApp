package fr.androidmakers.store.graphql

import android.content.Context
import android.service.autofill.UserData
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
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.store.firebase.FirebaseUserRepository
import java.util.PrimitiveIterator

actual class ApolloClientBuilder(
  context: Context,
  private val url: String,
  private val conference: String,
  private val userRepository: UserRepository,
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
}

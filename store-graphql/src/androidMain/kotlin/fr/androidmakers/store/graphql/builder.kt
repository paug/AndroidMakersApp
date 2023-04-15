package fr.androidmakers.store.graphql

import android.content.Context
import android.os.Build
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

fun GraphQLStore(context: Context): GraphQLStore {
  val cacheFactory = MemoryCacheFactory(20_000_000).chain(SqlNormalizedCacheFactory(context))
  val apolloClient = ApolloClient.Builder()
      .apply {
        if (Build.PRODUCT == "sdk_gphone64_arm64") {
          serverUrl("http://10.0.2.2:8080/graphql")
        } else {
          serverUrl("https://androidmakers-2023.ew.r.appspot.com/graphql")
        }
      }
      .addHttpInterceptor(object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
          return chain.proceed(
              request.newBuilder()
                  .addHeader("conference", "androidmakers2023")
                  .apply {
                    val token = runBlocking {
                      Firebase.auth.currentUser?.getIdToken(false)?.result?.token
                    }
                    if (token != null) {
                      addHeader("Authorization", "Bearer $token")
                    }
                  }
                  .build()
          )
        }
      })
      .normalizedCache(cacheFactory)
      .build()
  return GraphQLStore(apolloClient)
}
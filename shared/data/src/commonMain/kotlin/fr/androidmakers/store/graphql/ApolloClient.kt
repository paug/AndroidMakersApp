package fr.androidmakers.store.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.api.FieldRecordMerger
import com.apollographql.apollo3.cache.normalized.api.FieldRecordMerger.FieldMerger
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.MetadataGenerator
import com.apollographql.apollo3.cache.normalized.api.MetadataGeneratorContext
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import fr.androidmakers.domain.repo.UserRepository

expect suspend fun getIdToken(userRepository: UserRepository): String?

fun ApolloClient(
  sqlNormalizedCacheFactory: SqlNormalizedCacheFactory,
  userRepository: UserRepository,
): ApolloClient {
  val memoryCacheFactory = MemoryCacheFactory(20_000_000).chain(sqlNormalizedCacheFactory)
  @OptIn(ApolloExperimental::class)
  return ApolloClient.Builder()
    .serverUrl("https://androidmakers.fr/graphql")
    .addHttpInterceptor(object : HttpInterceptor {
      override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
      ): HttpResponse {
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
    .normalizedCache(
      normalizedCacheFactory = memoryCacheFactory,
      metadataGenerator = AndroidMakersMetaDataGenerator,
      recordMerger = FieldRecordMerger(AndroidMakersFieldMerger),
    )
    .build()
}

@OptIn(ApolloExperimental::class)
object AndroidMakersMetaDataGenerator : MetadataGenerator {
  @Suppress("UNCHECKED_CAST")
  override fun metadataForObject(obj: Any?, context: MetadataGeneratorContext): Map<String, Any?> {
    return if (context.field.type.rawType().name == "SessionConnection") {
      obj as Map<String, Any?>
      val pageInfo = obj["pageInfo"] as? Map<String, Any?>
      val endCursor = pageInfo?.get("endCursor") as? String
      mapOf(
        "endCursor" to endCursor,
        "after" to context.argumentValue("after"),
      )
    } else {
      emptyMap()
    }
  }
}

@OptIn(ApolloExperimental::class)
object AndroidMakersFieldMerger : FieldMerger {
  @Suppress("UNCHECKED_CAST")
  override fun mergeFields(
    existing: FieldRecordMerger.FieldInfo,
    incoming: FieldRecordMerger.FieldInfo
  ): FieldRecordMerger.FieldInfo {
    val existingEndCursor = existing.metadata["endCursor"] as? String
    val incomingAfterArgument = incoming.metadata["after"] as? String
    return if (existingEndCursor == null && incomingAfterArgument == null) {
      incoming
    } else if (incomingAfterArgument == existingEndCursor) {
      val existingValue = existing.value as Map<String, Any?>
      val existingNodes = existingValue["nodes"] as? List<*>

      val incomingValue = incoming.value as Map<String, Any?>
      val incomingNodes = incomingValue["nodes"] as? List<*>

      val mergedNodes: List<*> = existingNodes.orEmpty() + incomingNodes.orEmpty()
      val mergedValue = (existingValue + incomingValue).toMutableMap()
      mergedValue["nodes"] = mergedNodes

      FieldRecordMerger.FieldInfo(
        value = mergedValue,
        metadata = incoming.metadata,
      )
    } else {
      incoming
    }
  }
}

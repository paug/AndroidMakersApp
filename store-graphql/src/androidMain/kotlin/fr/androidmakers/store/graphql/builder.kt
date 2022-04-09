package fr.androidmakers.store.graphql

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.apolloStore
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory

fun GraphQLStore(context: Context): GraphQLStore {
    val cacheFactory = MemoryCacheFactory(20_000_000).chain(SqlNormalizedCacheFactory(context))
    val apolloClient = ApolloClient.Builder()
            .serverUrl("https://kiki-conf.ew.r.appspot.com/graphql")
            .normalizedCache(cacheFactory)
            .build()
    apolloClient.apolloStore.clearAll()
    return GraphQLStore(apolloClient)
}
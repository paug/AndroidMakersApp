package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import fr.androidmakers.store.graphql.ApolloClient
import fr.androidmakers.store.local.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val dataPlatformModule = module {
  single {
    ApolloClient(
      SqlNormalizedCacheFactory(context = get()),
      get()
    )
  }

  single<DataStore<Preferences>> {
    createDataStore {
      androidContext().filesDir.resolve("bookmarks.preferences_pb").absolutePath
    }
  }
}

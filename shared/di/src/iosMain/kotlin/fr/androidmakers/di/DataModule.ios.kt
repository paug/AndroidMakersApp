package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import fr.androidmakers.store.graphql.ApolloClient
import fr.androidmakers.store.local.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val dataPlatformModule = module {
  single {
    ApolloClient(
      SqlNormalizedCacheFactory(),
      get()
    )
  }

  single<DataStore<Preferences>> {
    createDataStore {
      val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
          directory = NSDocumentDirectory,
          inDomain = NSUserDomainMask,
          appropriateForURL = null,
          create = false,
          error = null,
      )
      requireNotNull(documentDirectory).path + "/bookmarks.preferences_pb"
    }
  }
}

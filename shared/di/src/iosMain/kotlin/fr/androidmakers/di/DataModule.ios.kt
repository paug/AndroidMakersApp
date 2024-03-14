package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import fr.androidmakers.store.graphql.ApolloClientBuilder
import fr.androidmakers.store.local.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val dataPlatformModule = module {
  single { ApolloClientBuilder("https://androidmakers-2023.ew.r.appspot.com/graphql", "androidmakers2023", "") }

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

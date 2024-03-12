package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import fr.androidmakers.store.graphql.ApolloClientBuilder
import fr.androidmakers.store.local.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val dataPlatformModule = module {
  single { ApolloClientBuilder(
      androidContext(),
      "https://androidmakers-2023.ew.r.appspot.com/graphql",
      "androidmakers2023")
  }

  single<DataStore<Preferences>> {
    createDataStore {
      androidContext().filesDir.resolve("bookmarks.preferences_pb").absolutePath
    }
  }
}

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
    "https://confetti-app.dev/graphql",
      "androidmakers2024")
  }

  single<DataStore<Preferences>> {
    createDataStore {
      androidContext().filesDir.resolve("bookmarks.preferences_pb").absolutePath
    }
  }
}

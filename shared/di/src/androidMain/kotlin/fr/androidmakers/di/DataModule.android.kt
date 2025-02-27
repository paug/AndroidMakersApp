package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import fr.androidmakers.store.graphql.ApolloClient
import fr.androidmakers.store.wear.WearMessaging
import fr.androidmakers.store.wear.WearMessagingImpl
import okio.Path.Companion.toOkioPath
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
    PreferenceDataStoreFactory.createWithPath {
      androidContext().filesDir.toOkioPath() / "bookmarks.preferences_pb"
    }
  }

  factory<WearMessaging> {
    WearMessagingImpl(get())
  }
}

package fr.androidmakers.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.apollographql.cache.normalized.sql.SqlNormalizedCacheFactory
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.store.graphql.ApolloClient
import fr.androidmakers.store.wear.WearMessaging
import fr.androidmakers.store.wear.WearMessagingImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okio.Path.Companion.toPath
import org.koin.dsl.module

private class DesktopUserRepository : UserRepository {
  override val currentUser: User? = null
  override val user: Flow<User?> = flowOf(null)
  override suspend fun getIdToken(): String? = null
}

actual val dataPlatformModule = module {
  single<UserRepository> { DesktopUserRepository() }

  single {
    ApolloClient(
      SqlNormalizedCacheFactory(),
      get()
    )
  }

  single<DataStore<Preferences>> {
    PreferenceDataStoreFactory.createWithPath {
      val userHome = System.getProperty("user.home")
      "$userHome/.androidmakers/bookmarks.preferences_pb".toPath()
    }
  }

  single<WearMessaging> {
    WearMessagingImpl()
  }
}

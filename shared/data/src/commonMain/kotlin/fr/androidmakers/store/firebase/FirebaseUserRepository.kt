package fr.androidmakers.store.firebase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FirebaseUserRepository(
  private val dataStore: DataStore<Preferences>,
) : UserRepository {
  // Use Firebase.auth as single source of truth

  override val currentUser: User?
    get() = Firebase.auth.currentUser?.toUser()

  override val user: Flow<User?>
    get() = Firebase.auth.authStateChanged.map { it?.toUser() }

  override suspend fun getIdToken(): String? {
    return try {
      Firebase.auth.currentUser?.getIdToken(false)
    } catch (e: CancellationException) {
      throw e
    } catch (ignore: Exception) {
      // See https://github.com/firebase/firebase-android-sdk/issues/5328#issuecomment-1719386926
      null
    }
  }

  @OptIn(ExperimentalUuidApi::class)
  override suspend fun getInstallationId(): String {
    val prefs = dataStore.data.first()
    prefs[INSTALLATION_ID_KEY]?.let { return it }

    val newId = Uuid.random().toString()
    dataStore.edit { it[INSTALLATION_ID_KEY] = newId }
    return newId
  }

  companion object {
    private val INSTALLATION_ID_KEY = stringPreferencesKey("installation_id")
  }
}

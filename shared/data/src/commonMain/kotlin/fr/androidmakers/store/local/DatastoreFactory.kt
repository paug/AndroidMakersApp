package fr.androidmakers.store.local

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(
    migrations: List<DataMigration<Preferences>> = emptyList(),
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = migrations,
    produceFile = { producePath().toPath() },
)

expect class DataStoreMigrationFactory {
  fun produceMigrations(name: String): List<DataMigration<Preferences>>
}

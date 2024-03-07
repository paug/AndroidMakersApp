package fr.androidmakers.store.local

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.Preferences

actual class DataStoreMigrationFactory {
  actual fun produceMigrations(name: String): List<DataMigration<Preferences>> = emptyList()
}

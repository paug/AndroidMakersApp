package fr.paug.androidmakers.wear.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import org.jraf.android.kprefs.Prefs

class LocalPreferencesRepository(applicationContext: Context) {
  private val localPrefs = Prefs(applicationContext)

  val showOnlyBookmarkedSessions: MutableStateFlow<Boolean> by localPrefs.BooleanFlow(false)
}

package fr.androidmakers.store.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BookmarksStore(private val dataStore: DataStore<Preferences>) {
  private var bookmarkedSessions: MutableSet<String> = HashSet()

  private var selectedSessionIds: MutableStateFlow<Set<String>>

  init {
    val prefSet = runBlocking {
      dataStore.data.map { prefs ->
        prefs[stringSetPreferencesKey(PREF_SELECTED_SESSIONS)]
      }.first()
    }
    if (prefSet != null) {
      bookmarkedSessions.addAll(prefSet)
    }

    selectedSessionIds = MutableStateFlow(
        mutableSetOf<String>().apply {
          addAll(bookmarkedSessions)
        }
    )
  }

  suspend fun setBookmarked(sessionId: String, bookmarked: Boolean) {
    if (bookmarked) {
      bookmarkedSessions.add(sessionId)
    } else {
      bookmarkedSessions.remove(sessionId)
    }
    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()
  }

  fun isBookmarked(id: String): Boolean {
    return bookmarkedSessions.contains(id)
  }

  fun subscribe(id: String): Flow<Boolean> {
    return MappedStateFlow(selectedSessionIds.asStateFlow()) {
      it.contains(id)
    }
  }

  private suspend fun save() {
    dataStore.edit { prefs ->
      prefs[stringSetPreferencesKey(PREF_SELECTED_SESSIONS)] = bookmarkedSessions
    }
  }

  /**
   * This is called after a successful signin or at startup to merge the remote bookmarks into
   * the local ones
   */
  suspend fun merge(bookmarks: Set<String>) {
    bookmarkedSessions.addAll(bookmarks)

    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()
  }

  companion object {
    private const val PREF_SELECTED_SESSIONS = "selected_sessions"
  }
}

private class MappedStateFlow<T, R>(
    private val source: StateFlow<T>,
    private val mapper: (T) -> R
) : StateFlow<R> {

  override val value: R
    get() = mapper(source.value)

  override val replayCache: List<R>
    get() = source.replayCache.map(mapper)

  override suspend fun collect(collector: FlowCollector<R>): Nothing {
    source.collect { value -> collector.emit(mapper(value)) }
  }
}

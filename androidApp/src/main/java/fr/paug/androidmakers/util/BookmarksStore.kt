package fr.paug.androidmakers.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import fr.paug.androidmakers.AndroidMakersApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object BookmarksStore {

  private const val PREF_SELECTED_SESSIONS = "selected_sessions"

  private var mSharedPreferences: SharedPreferences? = null

  private var bookmarkedSessions: MutableSet<String> = HashSet()

  private lateinit var selectedSessionIds: MutableStateFlow<Set<String>>

  fun init(context: Context) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    mSharedPreferences = sharedPreferences
    val prefSet = sharedPreferences.getStringSet(PREF_SELECTED_SESSIONS, null)
    if (prefSet != null) {
      bookmarkedSessions.addAll(prefSet)
    }

    selectedSessionIds = MutableStateFlow(
        mutableSetOf<String>().apply {
          addAll(bookmarkedSessions)
        }
    )
  }

  fun setBookmarked(sessionId: String, bookmarked: Boolean) {
    if (bookmarked) {
      bookmarkedSessions.add(sessionId)
    } else {
      bookmarkedSessions.remove(sessionId)
    }
    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()

    // YOLO 🙌
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null) {
      GlobalScope.launch {
        AndroidMakersApplication.instance().sessionsRepository.setBookmark(userId, sessionId, bookmarked)
      }
    }
  }

  fun isBookmarked(id: String): Boolean {
    return bookmarkedSessions.contains(id)
  }

  fun subscribe(id: String): Flow<Boolean> {
    return MappedStateFlow(selectedSessionIds.asStateFlow()) {
      it.contains(id)
    }
  }

  private fun save() {
    val editor = mSharedPreferences?.edit()
    editor?.putStringSet(PREF_SELECTED_SESSIONS, bookmarkedSessions)
    editor?.apply()
  }

  /**
   * This is called after a successful signin or at startup to merge the remote bookmarks into
   * the local ones
   */
  fun merge(bookmarks: Set<String>) {
    bookmarkedSessions.addAll(bookmarks)

    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()
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

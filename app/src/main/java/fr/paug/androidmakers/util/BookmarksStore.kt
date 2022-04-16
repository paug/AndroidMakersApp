package fr.paug.androidmakers.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlinx.coroutines.flow.*

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
}

private class MappedStateFlow<T, R>(private val source: StateFlow<T>, private val mapper: (T) -> R) : StateFlow<R> {

    override val value: R
        get() = mapper(source.value)

    override val replayCache: List<R>
        get() = source.replayCache.map(mapper)

    override suspend fun collect(collector: FlowCollector<R>): Nothing {
        source.collect { value -> collector.emit(mapper(value)) }
    }
}
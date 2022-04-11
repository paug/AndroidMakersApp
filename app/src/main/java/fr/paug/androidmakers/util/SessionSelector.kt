package fr.paug.androidmakers.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

object SessionSelector {

    private const val PREF_SELECTED_SESSIONS = "selected_sessions"

    private var mSharedPreferences: SharedPreferences? = null

    private var mSessionsSelected: MutableSet<String> = HashSet()

    private lateinit var selectedSessionIds: MutableStateFlow<Set<String>>

    val sessionsSelected: Set<String>
        get() = mSessionsSelected

    fun init(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mSharedPreferences = sharedPreferences
        val prefSet = sharedPreferences.getStringSet(PREF_SELECTED_SESSIONS, null)
        if (prefSet != null) {
            mSessionsSelected.addAll(prefSet)
        }

        selectedSessionIds = MutableStateFlow(
            mutableSetOf<String>().apply {
                addAll(mSessionsSelected)
            }
        )
    }

    fun setSessionSelected(id: String, selected: Boolean) {
        if (selected) {
            mSessionsSelected.add(id)
        } else {
            mSessionsSelected.remove(id)
        }
        selectedSessionIds.value = mutableSetOf<String>().apply { addAll(mSessionsSelected) }
        save()
    }

    private var index = 0
    fun isSelected(id: String): Boolean {
        return mSessionsSelected.contains(id)
    }

    fun selected(id: String): Flow<Boolean> {
        return MappedStateFlow(selectedSessionIds.asStateFlow()) {
            it.contains(id)
        }
    }
    private fun save() {
        val editor = mSharedPreferences?.edit()
        editor?.putStringSet(PREF_SELECTED_SESSIONS, mSessionsSelected)
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
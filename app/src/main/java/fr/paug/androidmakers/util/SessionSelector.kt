package fr.paug.androidmakers.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

object SessionSelector {

    private const val PREF_SELECTED_SESSIONS = "selected_sessions"

    private var mSharedPreferences: SharedPreferences? = null

    private var mSessionsSelected: MutableSet<String> = HashSet()

    val sessionsSelected: Set<String>?
        get() = mSessionsSelected

    fun init(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mSharedPreferences = sharedPreferences
        val prefSet = sharedPreferences.getStringSet(PREF_SELECTED_SESSIONS, null)
        if (prefSet != null) {
            mSessionsSelected.addAll(prefSet)
        }
    }

    fun setSessionSelected(id: String, selected: Boolean) {
        if (selected) {
            mSessionsSelected.add(id)
        } else {
            mSessionsSelected.remove(id)
        }
        save()
    }

    fun isSelected(id: String): Boolean {
        return mSessionsSelected.contains(id)
    }

    private fun save() {
        val editor = mSharedPreferences?.edit()
        editor?.putStringSet(PREF_SELECTED_SESSIONS, mSessionsSelected)
        editor?.apply()
    }

}
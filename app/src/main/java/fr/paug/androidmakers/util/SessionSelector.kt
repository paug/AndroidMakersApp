package fr.paug.androidmakers.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

object SessionSelector
{

  private const val PREF_SELECTED_SESSIONS = "selected_sessions"

  private var mSharedPreferences: SharedPreferences? = null

  private var mSessionsSelected: MutableSet<String>? = null

  val sessionsSelected: Set<String>?
    get() = mSessionsSelected

  fun init(context: Context)
  {
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    mSessionsSelected = HashSet()
    val prefSet = mSharedPreferences!!.getStringSet(PREF_SELECTED_SESSIONS, null)
    if (prefSet != null)
    {
      mSessionsSelected!!.addAll(prefSet)
    }
  }

  fun setSessionSelected(id: String, selected: Boolean)
  {
    //TODO use AndroidMakersStore
    //        val idString = AgendaRepository.CURRENT_YEAR_NODE + "_" + id
    if (selected)
    {
      mSessionsSelected!!.add(id)
    }
    else
    {
      mSessionsSelected!!.remove(id)
    }
    save()
  }

  fun isSelected(id: String): Boolean
  {
    return mSessionsSelected!!.contains(id) //contains(AgendaRepository.CURRENT_YEAR_NODE + "_" + id)
  }

  private fun save()
  {
    val editor = mSharedPreferences!!.edit()
    editor.putStringSet(PREF_SELECTED_SESSIONS, mSessionsSelected)
    editor.apply()
  }

}
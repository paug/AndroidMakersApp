package fr.paug.androidmakers.manager

import android.util.Log
import android.util.SparseArray
import com.google.firebase.firestore.FirebaseFirestore

import java.util.ArrayList
import fr.paug.androidmakers.model.FirebaseDataConverted
import fr.paug.androidmakers.model.PartnerGroup
import fr.paug.androidmakers.model.Room
import fr.paug.androidmakers.model.ScheduleSlot
import fr.paug.androidmakers.model.Session
import fr.paug.androidmakers.model.Speaker
import fr.paug.androidmakers.model.Venue

class AgendaRepository private constructor() {

    private val mDatabase = FirebaseFirestore.getInstance()

    private val mOnLoadListeners = mutableListOf<OnLoadListener>()
    var isLoaded: Boolean = false
        private set
    private val mFirebaseDataConverted = FirebaseDataConverted()

    val allRooms: SparseArray<Room>
        get() = mFirebaseDataConverted.rooms

    val scheduleSlots: List<ScheduleSlot>
        get() = ArrayList(mFirebaseDataConverted.scheduleSlots)

    val partners: Map<PartnerGroup.PartnerType, PartnerGroup>
        get() = mFirebaseDataConverted.partners

    val allLanguages: Set<String>
        get() = mFirebaseDataConverted.allLanguages

    fun load(listener: OnLoadListener) {
        if (isLoaded) {
            listener.onAgendaLoaded()
            return
        }
        mOnLoadListeners.add(listener)
    }

    fun getRoom(id: Int): Room? {
        return mFirebaseDataConverted.rooms.get(id)
    }

    fun getSession(id: Int): Session? {
        return mFirebaseDataConverted.sessions.get(id)
    }

    fun getSpeaker(id: Int): Speaker? {
        return mFirebaseDataConverted.speakers.get(id)
    }

    fun getVenue(id: Int): Venue? {
        return mFirebaseDataConverted.venues.get(id)
    }

    fun getScheduleSlot(id: Int): ScheduleSlot? {
        for (slot in mFirebaseDataConverted.scheduleSlots) {
            if (slot.sessionId == id) {
                return slot
            }
        }
        return null
    }

    fun getScheduleSlot(id: String): ScheduleSlot? {
        try {
            val idAsInt = Integer.parseInt(id)
            return getScheduleSlot(idAsInt)
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Cannot format " + id + "into an int: ", e)
        }

        return null
    }

    fun removeListener(listener: OnLoadListener) {
        mOnLoadListeners.remove(listener)
    }

    interface OnLoadListener {
        fun onAgendaLoaded()
    }

    companion object {
        private val TAG = "AgendaRepository"
        private var _instance: AgendaRepository? = null

        val CURRENT_YEAR_NODE = "2018"

        val instance: AgendaRepository
            get() {
            if (_instance == null) {
                _instance = AgendaRepository()
            }
            return _instance!!
        }
    }
}

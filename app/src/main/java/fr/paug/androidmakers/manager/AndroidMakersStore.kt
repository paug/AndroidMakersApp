package fr.paug.androidmakers.manager

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import fr.paug.androidmakers.model.*
import io.openfeedback.android.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AndroidMakersStore {
    fun getVenue(document: String, callback: (Venue?) -> Unit) {
        FirebaseSingleton.firestore.collection("venues").document(document).get()
                .addOnSuccessListener { venueDocument ->
                    if (venueDocument != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + venueDocument.data)
                        val venue = venueDocument.toObject(Venue::class.java)
                        callback.invoke(venue)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getSessions(callback: (HashMap<String, SessionKt>) -> Unit) {
        val allSessions = HashMap<String, SessionKt>()
        FirebaseSingleton.firestore.collection("sessions")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val session = document.toObject(SessionKt::class.java)
                        allSessions[document.id] = session
                        Log.e("session", session.toString())
                    }
                    callback.invoke(allSessions)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    fun getSession(id: String): Flow<SessionKt> {
        return FirebaseSingleton.firestore.collection("sessions")
                .document(id)
                .toFlow()
                .mapNotNull {
                    it.toObject(SessionKt::class.java)
                }
    }

    fun getSession(id: String, callback: (SessionKt?) -> Unit) {
        FirebaseSingleton.firestore.collection("sessions").document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)
                        val session = document.toObject(SessionKt::class.java)
                        callback.invoke(session)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getSlotsFlow(): Flow<List<ScheduleSlotKt>> {
        val flow1 = FirebaseSingleton.firestore
                .collection("schedule").document(DAY1)
                .toFlow()

        val flow2 = FirebaseSingleton.firestore
                .collection("schedule").document(DAY2)
                .toFlow()

        return combine(flow1, flow2) { result1, result2 ->
            convertResults(mapOf(
                    DAY1 to result1,
                    DAY2 to result2
            ))
        }
    }

    class Agenda(
            val sessions: Map<String, SessionKt>,
            val slots: List<ScheduleSlotKt>,
            val rooms: List<RoomKt>,
            val speakers: Map<String, SpeakerKt>
    )

    fun getAgendaFlow(): Flow<Agenda> {
        val sessionsFlow = FirebaseSingleton.firestore.collection("sessions")
                .toFlow()
                .map { result ->
                    result.map { it.id to it.toObject(SessionKt::class.java) }
                            .toMap()
                }
        val slotsFlow = getSlotsFlow()

        val roomsFlow = FirebaseSingleton.firestore.collection("schedule-app").document("rooms")
                .toFlow()
                .map { result ->
                    result.toObject(RoomsList::class.java)!!.allRooms
                }

        val speakersFlow = FirebaseSingleton.firestore.collection("speakers")
                .toFlow()
                .map { result ->
                    result.map { it.id to it.toObject(SpeakerKt::class.java) }
                            .toMap()
                }

        return combine(sessionsFlow, slotsFlow, roomsFlow, speakersFlow) { sessions, slots, rooms, speakers ->
            Agenda(sessions, slots, rooms, speakers)
        }

    }

    @Deprecated(message = "Use the coroutines version instead", replaceWith = ReplaceWith("getSlots()"))
    fun getSlotsFlow(callback: (List<ScheduleSlotKt>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val slots = getSlotsFlow().first()
            callback(slots)
        }
    }

    private fun convertResults(results: Map<String, DocumentSnapshot>): List<ScheduleSlotKt> {
        val list = mutableListOf<ScheduleSlotKt>()
        for (result in results) {
            val day = result.value.data!!
            val timeSlots = day.getAsListOfMaps("timeslots")

            timeSlots.forEachIndexed { timeSlotIndex, timeSlot ->
                val sessions = timeSlot.getAsListOfMaps("sessions")
                sessions.forEachIndexed { index, session ->
                    val sessionId = session.getAsListOfStrings("items").firstOrNull()
                    if (sessionId != null) {
                        val startTime = timeSlot.getAsString("startTime")

                        val extend = (session.get("extend") as Long?)?.toInt()?.minus(1) ?: 0
                        val endTime = timeSlots[timeSlotIndex + extend].getAsString("endTime")
                        val roomId = when {
                            sessions.size == 1 -> "all"
                            index == 0 -> "moebius"
                            index == 1 -> "blin"
                            index == 2 -> "202"
                            index == 3 -> "204"
                            index == 4 -> "office"
                            else -> throw Exception("no room found")
                        }

                        list.add(ScheduleSlotKt(
                                startDate = getDate(result.key, startTime),
                                endDate = getDate(result.key, endTime),
                                roomId = roomId,
                                sessionId = sessionId))
                    }
                }
            }
        }

        return list
    }


    /**
     * @param date the date as YYYY-MM-DD
     * @param time the time as HH:mm
     * @return a ISO86-01 String
     */
    private fun getDate(date: String, time: String): String {
        val d = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse("$date $time")

        val tz = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        df.timeZone = tz
        return df.format(d)
    }

    fun getSpeakers(callback: (HashMap<String, SpeakerKt>) -> Unit) {
        val allSpeakers = HashMap<String, SpeakerKt>()
        FirebaseSingleton.firestore.collection("speakers").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val speaker = document.toObject(SpeakerKt::class.java)
                        allSpeakers[document.id] = speaker
                        Log.e("speaker", speaker.toString())
                    }
                    callback.invoke(allSpeakers)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getSpeaker(id: String, callback: (SpeakerKt?) -> Unit) {
        FirebaseSingleton.firestore.collection("speakers").document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)
                        val speaker = document.toObject(SpeakerKt::class.java)
                        callback.invoke(speaker)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getRooms(callback: (List<RoomKt>) -> Unit) {
        val allRooms = mutableListOf<RoomKt>()
        FirebaseSingleton.firestore.collection("schedule-app").document("rooms")
                .get()
                .addOnSuccessListener { result ->
                    Log.e("result", result.toString())
                    val rooms = result.toObject(RoomsList::class.java)
                    for (room in rooms!!.allRooms) {
                        allRooms.add(room)
                    }
                    callback.invoke(allRooms)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getRoom(roomId: String): Flow<RoomKt> {
        return FirebaseSingleton.firestore.collection("schedule-app").document("rooms")
                .toFlow()
                .mapNotNull {
                    it.toObject(RoomsList::class.java)?.allRooms
                }.mapNotNull {
                    it.firstOrNull { it.roomId == roomId }
                }
    }

    fun getRoom(roomId: String, callback: (RoomKt?) -> Unit) {
        FirebaseSingleton.firestore.collection("schedule-app").document("rooms")
                .get()
                .addOnSuccessListener { result ->
                    Log.e("result", result.toString())
                    val rooms = result.toObject(RoomsList::class.java)
                    for (room in rooms!!.allRooms) {
                        if (room.roomId == roomId) {
                            callback.invoke(room)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    companion object {
        private const val DAY1 = "2020-04-20"
        private const val DAY2 = "2020-04-21"
        private const val TAG = "Firestore"
    }
}

fun <K> Map<K, *>.getAsMap(k: K): Map<String, *> {
    return this.get(k) as Map<String, *>
}

fun <K> Map<K, *>.getAsListOfMaps(k: K): List<Map<String, *>> {
    return this.get(k) as List<Map<String, *>>
}

fun <K> Map<K, *>.getAsListOfStrings(k: K): List<String> {
    return this.get(k) as List<String>
}

fun <K> Map<K, *>.getAsString(k: K): String {
    return this.get(k) as String
}

package fr.paug.androidmakers.manager

import com.google.firebase.firestore.DocumentSnapshot
import fr.paug.androidmakers.model.*
import fr.paug.androidmakers.util.toFlow
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class AndroidMakersStore {
    fun getVenue(document: String): Flow<Venue> {
        return FirebaseSingleton.firestore.collection("venues")
            .document(document)
            .toFlow()
            .mapNotNull {
                it.toObject(Venue::class.java)
            }
    }

    fun getSpeaker(id: String): Flow<Speaker> {
        return FirebaseSingleton.firestore.collection("speakers")
            .document(id)
            .toFlow()
            .mapNotNull {
                it.toObject(Speaker::class.java)
            }
    }

    fun getRoom(roomId: String): Flow<Room> {
        return flowOf(allRooms[roomId]).filterNotNull()
    }

    fun getSession(id: String): Flow<Session> {
        return FirebaseSingleton.firestore.collection("sessions")
            .document(id)
            .toFlow()
            .mapNotNull {
                it.toObject(Session::class.java)
            }
    }

    fun getSlotsFlow(): Flow<List<ScheduleSlot>> {
        val flow1 = FirebaseSingleton.firestore
            .collection("schedule").document(DAY1)
            .toFlow()

        val flow2 = FirebaseSingleton.firestore
            .collection("schedule").document(DAY2)
            .toFlow()

        return combine(flow1, flow2) { result1, result2 ->
            convertResults(
                mapOf(
                    DAY1 to result1,
                    DAY2 to result2
                )
            )
        }.filterNotNull()
    }

    private val allRooms = mapOf(
        "0" to Room("Track 1"),
        "1" to Room("Track 2"),
        "2" to Room("Track 3"),
        "3" to Room("Track 4"),
        "4" to Room("Track 5"), // not needed theorically but added in case we decide to add new tracks
        "5" to Room("Track 6"),
        "6" to Room("Track 7"),
        "6" to Room("Track 8"),
        ROOM_ID_ALL to Room("All")
    )

    fun getAgendaFlow(): Flow<Agenda> {
        val sessionsFlow = FirebaseSingleton.firestore.collection("sessions")
            .toFlow()
            .map { result ->
                result.map { it.id to it.toObject(Session::class.java) }
                    .toMap()
            }
        val slotsFlow = getSlotsFlow()

        val roomsFlow = flowOf(allRooms)

        val speakersFlow = FirebaseSingleton.firestore.collection("speakers")
            .toFlow()
            .map { result ->
                result.map { it.id to it.toObject(Speaker::class.java) }
                    .toMap()
            }

        return combine(
            sessionsFlow,
            slotsFlow,
            roomsFlow,
            speakersFlow
        ) { sessions, slots, rooms, speakers ->
            Agenda(sessions, slots, rooms, speakers)
        }

    }

    private fun convertResults(results: Map<String, DocumentSnapshot>): List<ScheduleSlot>? {
        val list = mutableListOf<ScheduleSlot>()
        for (result in results) {
            val day = result.value.data
            if (day == null) {
                return null
            }
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
                            else -> index.toString()
                        }

                        list.add(
                            ScheduleSlot(
                                startDate = getDate(result.key, startTime),
                                endDate = getDate(result.key, endTime),
                                roomId = roomId,
                                sessionId = sessionId
                            )
                        )
                    }
                }
            }
        }

        return list.filter {
            it.sessionId != "no-op"
        }
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


    companion object {
        const val ROOM_ID_ALL = "all"
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

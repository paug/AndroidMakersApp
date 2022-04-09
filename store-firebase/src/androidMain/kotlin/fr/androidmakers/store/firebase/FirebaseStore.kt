package fr.androidmakers.store.firebase

import com.google.firebase.firestore.DocumentSnapshot
import fr.androidmakers.store.model.*
import fr.androidmakers.store.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class FirebaseStore : AndroidMakersStore {
    override fun getVenue(id: String): Flow<Venue> {
        return FirebaseSingleton.firestore.collection("venues")
            .document(id)
            .toFlow()
            .mapNotNull {
                it.toObject(Venue::class.java)
            }
    }

    override fun getSpeaker(id: String): Flow<Speaker> {
        return FirebaseSingleton.firestore.collection("speakers")
            .document(id)
            .toFlow()
            .mapNotNull {
                it.toObject(Speaker::class.java)
            }
    }

    override fun getRoom(id: String): Flow<Room> {
        return flowOf(allRooms.first { it.id == id })
    }

    override fun getRooms(): Flow<List<Room>> {
        return flowOf(allRooms)
    }

    override fun getSession(id: String): Flow<Session> {
        return FirebaseSingleton.firestore.collection("sessions")
            .document(id)
            .toFlow()
            .mapNotNull {
                it.toObject(Session::class.java)
            }
    }

    override fun getPartners(): Flow<List<Partner>> {
        return FirebaseSingleton.firestore.collection("partners")
            .toFlow()
            .map {
                it.documents.mapNotNull { it.toObject(Partner::class.java) }
            }
    }

    override fun getScheduleSlots(): Flow<List<ScheduleSlot>> {
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

    private val allRooms = listOf(
        Room("0", "Track 1"),
        Room("1", "Track 2"),
        Room("2", "Track 3"),
        Room("3", "Track 4"),
        Room("4", "Track 5"), // not needed theoretically but added in case we decide to add new tracks
        Room("5", "Track 6"),
        Room("6", "Track 7"),
        Room("7", "Track 8"),
        Room(ROOM_ID_ALL,"All")
    )

    override fun getSessions(): Flow<List<Session>> {
        return FirebaseSingleton.firestore.collection("sessions")
            .toFlow()
            .map { result ->
                result.documents.mapNotNull { it.toObject(Session::class.java)?.copy(id = it.id) }
            }
    }

    override fun getSpeakers(): Flow<List<Speaker>> {
        return FirebaseSingleton.firestore.collection("speakers")
            .toFlow()
            .map { result ->
                result.documents.mapNotNull { it.toObject(Speaker::class.java)?.copy(id = it.id) }
            }
    }

    private fun convertResults(results: Map<String, DocumentSnapshot>): List<ScheduleSlot>? {
        val list = mutableListOf<ScheduleSlot>()
        for (result in results) {
            val day = result.value.data ?: return null
            val timeSlots = day.getAsListOfMaps("timeslots")

            timeSlots.forEachIndexed { timeSlotIndex, timeSlot ->
                val sessions = timeSlot.getAsListOfMaps("sessions")
                sessions.forEachIndexed { index, session ->
                    val sessionId = session.getAsListOfStrings("items").firstOrNull()
                    if (sessionId != null) {
                        val startTime = timeSlot.getAsString("startTime")

                        val extend = (session.get("extend") as Long?)?.toInt()?.minus(1) ?: 0
                        val endTime = timeSlots[timeSlotIndex + extend].getAsString("endTime")
                        val roomId = when (sessions.size) {
                            1 -> "all"
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
        val d = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse("$date $time") ?: error("Cannot parse $date")

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

private fun <K> Map<K, *>.getAsMap(k: K): Map<String, *> {
    @Suppress("UNCHECKED_CAST")
    return this.get(k) as Map<String, *>
}

private fun <K> Map<K, *>.getAsListOfMaps(k: K): List<Map<String, *>> {
    @Suppress("UNCHECKED_CAST")
    return this.get(k) as List<Map<String, *>>
}

private fun <K> Map<K, *>.getAsListOfStrings(k: K): List<String> {
    @Suppress("UNCHECKED_CAST")
    return this.get(k) as List<String>
}

fun <K> Map<K, *>.getAsString(k: K): String {
    return this.get(k) as String
}

import org.junit.Test
import com.google.firebase.FirebaseApp
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream
import com.google.firebase.cloud.FirestoreClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio
import java.io.File


class Main {
    val root = File("/Users/martin/git/firestoreUpload/")
    val moshi = Moshi.Builder().build()
    val listAdapter = moshi.adapter<List<Map<*,*>>>(Types.newParameterizedType(List::class.java, Map::class.java))
    val mapAdapter = moshi.adapter<Map<String,*>>(Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java))

    fun doUpload(collection: String, document: String, slots: Map<String, Any>) {
        val serviceAccount = FileInputStream(File(root, "android-makers-2019-firebase-adminsdk-4d6n6-fa4b43900f.json"))

        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://android-makers-2019.firebaseio.com")
                .build()

        FirebaseApp.initializeApp(options)
        val db = FirestoreClient.getFirestore()
        val docRef = db.collection(collection).document(document)
        val result = docRef.set(slots)

        System.out.println("Update time : " + result.get().updateTime)
    }

    @Test
    fun convert() {
        val source = Okio.buffer(Okio.source(File(root, "schedule.json")))
        val map = source.use {
            mapAdapter.fromJson(it)
        }!!

        val list = mutableListOf<Map<*,*>>()
        map.keys.forEach {d ->
            val day = map.getAsMap(d)
            val timeSlots = day.getAsListOfMaps("timeslots")

            timeSlots.forEachIndexed {timeSlotIndex, timeSlot ->
                val sessions = timeSlot.getAsListOfMaps("sessions")
                sessions.forEachIndexed {index, session ->
                    val slot = mutableMapOf<String, String>()
                    val sessionId = session.getAsListOfStrings("items").firstOrNull()
                    if (sessionId != null) {
                        slot.put("startTime", timeSlot.getAsString("startTime"))

                        val extend = (session.get("extend") as Double?)?.toInt()?.minus(1) ?: 0
                        slot.put("endTime", timeSlots[timeSlotIndex + extend].getAsString("endTime"))
                        slot.put("sessionId", sessionId)
                        val roomId = when {
                            sessions.size == 1 -> "all"
                            index == 0 -> "moebius"
                            index == 1 -> "blin"
                            index == 2 -> "202"
                            index == 3 -> "204"
                            else -> throw Exception("no room found")
                        }
                        slot.put("roomId", roomId)

                        list.add(slot)
                    }
                }
            }
        }

        System.out.println(listAdapter.indent("    ").toJson(list))
    }

    @Test
    fun upload() {
        val source = Okio.buffer(Okio.source(File(root, "schedule-app.json")))
        val slots = source.use {
            listAdapter.fromJson(it)
        }!!

        doUpload("schedule-app", "slots", mapOf("all" to slots))
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


package fr.paug.androidmakers.manager

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import fr.paug.androidmakers.model.*

class AndroidMakersStore {

    fun getPartners(callback: (PartnerCollection) -> Unit) {
        FirebaseFirestore.getInstance().collection("partners")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, document.id + " => " + document.data)
                        val partnerCollection = document.toObject(PartnerCollection::class.java)
                        callback.invoke(partnerCollection)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    fun getVenue(document: String, callback: (Venue?) -> Unit) {
        FirebaseFirestore.getInstance().collection("venues").document(document).get()
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
        FirebaseFirestore.getInstance().collection("sessions")
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

    fun getSession(id: String, callback: (SessionKt?) -> Unit) {
        FirebaseFirestore.getInstance().collection("sessions").document(id).get()
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

    fun getSlots(callback: (List<ScheduleSlotKt>) -> Unit) {
        val allSlots = mutableListOf<ScheduleSlotKt>()
        FirebaseFirestore.getInstance()
                .collection("schedule-app").document("slots")
                .get()
                .addOnSuccessListener { result ->
                    val slots = result.toObject(ScheduleSlotList::class.java)
                    for (scheduleSlotKt in slots!!.all) {
                        Log.e("slot", scheduleSlotKt.toString())
                        allSlots.add(scheduleSlotKt)
                    }
                    callback.invoke(allSlots)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    fun getSpeakers(callback: (HashMap<String, SpeakerKt>) -> Unit) {
        val allSpeakers = HashMap<String, SpeakerKt>()
        FirebaseFirestore.getInstance().collection("speakers").get()
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
        FirebaseFirestore.getInstance().collection("speakers").document(id).get()
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
        FirebaseFirestore.getInstance().collection("schedule-app").document("rooms")
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

    fun getRoom(roomId: String, callback: (RoomKt?) -> Unit) {
        FirebaseFirestore.getInstance().collection("schedule-app").document("rooms")
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
        private const val TAG = "Firestore"
    }

}
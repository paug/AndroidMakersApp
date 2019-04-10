package fr.paug.androidmakers.manager

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import fr.paug.androidmakers.model.*

class AndroidMakersStore {

    val TAG = "Firestore"

    fun getPartners(callback: (PartnerCollection) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("partners")
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

    /*
    rajouter cette règle dans firestore :
    match /venues/{id} {
    	allow read;
    }
     */
    fun getVenue(document: String, callback: (Venue?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("venues").document(document)
        docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.data)
                        val venue = document.toObject(Venue::class.java)
                        callback.invoke(venue)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
    }

    fun getSessions(callback: (HashMap<String, SessionKt>?) -> Unit) {
        val sessions = HashMap<String, SessionKt>()
        FirebaseFirestore.getInstance().collection("sessions")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val session = document.toObject(SessionKt::class.java)
                        sessions[document.id] = session
                        Log.e("session", session.toString())
                    }
                    callback.invoke(sessions)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    fun getSession(id: String, callback: (SessionKt?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("sessions").document(id)
        docRef.get()
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

    fun getSlots(callback: (List<ScheduleSlotKt>?) -> Unit) {
        var slots = mutableListOf<ScheduleSlotKt>()
        FirebaseFirestore.getInstance()
                .collection("schedule-app").document("slots")
                .get()
                .addOnSuccessListener { result ->
                    val allSlots = result.toObject(ScheduleSlotList::class.java)
                    for (scheduleSlotKt in allSlots!!.all) {
                        Log.e("slot", scheduleSlotKt.toString())
                        slots.add(scheduleSlotKt)
                    }
                    callback.invoke(slots)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

    fun getSpeaker(id: String, callback: (SpeakerKt?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("speakers").document(id)
        docRef.get()
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

    fun getRooms(callback: (List<RoomKt>?) -> Unit) {
        val allRooms = mutableListOf<RoomKt>()
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("schedule-app").document("rooms")
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
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("schedule-app").document("rooms")
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

}
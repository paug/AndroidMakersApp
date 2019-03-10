package fr.paug.androidmakers.manager

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import fr.paug.androidmakers.model.PartnerCollection
import fr.paug.androidmakers.model.Venue

//TODO get realtime data https://firebase.google.com/docs/firestore/query-data/listen
//TODO enable offline https://firebase.google.com/docs/firestore/manage-data/enable-offline
class AndroidMakersStore {

    val TAG = "Firestore"

    fun getFireStore(): CollectionReference {
        val firestore = FirebaseFirestore.getInstance()
        return firestore.collection("")
    }

    fun getSpeakers(): CollectionReference {
        val firestore = FirebaseFirestore.getInstance()
        return firestore.collection("speakers")
    }

    fun getPartners() : List<PartnerCollection> {
        val partnersList = mutableListOf<PartnerCollection>()
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("partners")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Firestore", document.id + " => " + document.data)
                        val partnerCollection = document.toObject(PartnerCollection::class.java)
                        partnersList.add(partnerCollection)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Error getting documents.", exception)
                }
        return partnersList
    }

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

    fun getVenues(callback: (Venue) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("venues")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, document.id + " => " + document.data)
                        val venue = document.toObject(Venue::class.java)
                        callback.invoke(venue)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
    }

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

}
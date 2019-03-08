package fr.paug.androidmakers.manager

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import fr.paug.androidmakers.model.PartnerCollection

class AndroidMakersStore {

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

}
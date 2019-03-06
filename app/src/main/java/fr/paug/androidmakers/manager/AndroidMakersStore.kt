package fr.paug.androidmakers.manager

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AndroidMakersStore {

    fun getFireStore() : CollectionReference {
        val firestore = FirebaseFirestore.getInstance()
        return firestore.collection("")
    }

    fun getSpeakers(): CollectionReference {
        val firestore = FirebaseFirestore.getInstance()
        return firestore.collection("speakers")
    }

}
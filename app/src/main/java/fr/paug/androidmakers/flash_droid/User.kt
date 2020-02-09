package fr.paug.androidmakers.flash_droid

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

object User {
    var document: MutableMap<String, Any?>? = null
    val iso8601DateFormat by lazy {
        val tz = TimeZone.getTimeZone("UTC")
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US)
        format.setTimeZone(tz)
        format
    }

    fun init(context: Context, callback: () -> Unit) {
        val user =  FirebaseAuth.getInstance().currentUser
        FirebaseAuth.getInstance().currentUser?.displayName

        if (user == null) {
            Log.e("User", "User not LoggedIn")
            Toast.makeText(context, "You need to log in", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseFirestore.getInstance().collection("flash-droid")
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    val doc = if (it.data == null) {
                        val newDoc = mutableMapOf(
                                "email" to user.email,
                                "displayName" to user.displayName,
                                "desserts" to mapOf<String, String>()
                        )
                        FirebaseFirestore.getInstance().collection("flash-droid")
                                .document(user.uid)
                                .set(newDoc)
                        newDoc
                    } else {
                        it.data!!.toMutableMap()
                    }

                    doc["desserts"] = (doc["desserts"] as? MutableMap<String, String>?)?.toMutableMap()
                    if (doc["desserts"] == null) {
                        doc["desserts"] = mutableMapOf<String, String>()
                    }
                    document = doc

                    callback.invoke()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Cannot retrieve dessert data", Toast.LENGTH_LONG).show()
                }
    }

    fun isReady(): Boolean {
        return document!= null
    }

    fun addDessert(context: Context, dessert: String) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            Log.e("User", "User not LoggedIn")
            Toast.makeText(context, "You need to log in", Toast.LENGTH_LONG).show()
            return
        }

        val doc = document
        if (doc == null) {
            Log.e("User", "User.addDessert called when User.isReady() is false")
            return
        }

        if ((doc["desserts"] as MutableMap<String, String>).containsKey(dessert)) {
            Log.d("User", "$dessert was already scanned, skip")
            return
        }

        (doc["desserts"] as MutableMap<String, String>).put(dessert, iso8601DateFormat.format(Date()))

        doc["email"] = user.email
        doc["displayName"] = user.displayName

        FirebaseFirestore.getInstance().collection("flash-droid")
                .document(user.uid)
                .set(doc)
    }

    fun desserts(): Map<String, String> {
        return document?.get("desserts") as? Map<String, String>? ?: emptyMap()
    }
}
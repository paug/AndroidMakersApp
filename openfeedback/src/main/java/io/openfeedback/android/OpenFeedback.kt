package io.openfeedback.android

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import io.openfeedback.android.model.Project
import io.openfeedback.android.model.TotalVotes
import io.openfeedback.android.model.VoteStatus
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import java.util.*

class OpenFeedback(context: Context,
                   applicationId: String,
                   projectId: String,
                   apiKey: String,
                   databaseUrl: String,
                   appName: String = "openfeedback") {

    val firestore: FirebaseFirestore
    val auth: FirebaseAuth

    init {
        val options = FirebaseOptions.Builder()
                .setProjectId(projectId)
                .setApplicationId(applicationId)
                .setApiKey(apiKey)
                .setDatabaseUrl(databaseUrl)
                .build()

        val app = FirebaseApp.initializeApp(context, options, appName)

        firestore = FirebaseFirestore.getInstance(app)

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()

        auth = FirebaseAuth.getInstance(app)
    }

    private suspend fun <R> withFirebaseUser(block: suspend (FirebaseUser) -> R?): R? {
        return getFirebaseUser()?.let { block.invoke(it) }
    }

    private var project: Project? = null

    private suspend fun getFirebaseUser() = Mutex().withLock {
        if (auth.currentUser == null) {
            val result = auth.signInAnonymously().await()
            if (result.user == null) {
                Log.e(OpenFeedback::class.java.name, "Cannot signInAnonymously")
            }
        }
        auth.currentUser
    }

    suspend fun getProject(projectId: String): Flow<Project> = flow {
        val task = firestore.collection("projects")
                .document(projectId)
                .get()
        val snapshot = task.await()

        emit(snapshot.toObject(Project::class.java)!!)
    }

    fun getMyVotes(projectId: String, sessionId: String) = flow {
        val user = getFirebaseUser()
        if (user != null) {
            firestore.collection("projects/$projectId/userVotes")
                    .whereEqualTo("userId", user.uid)
                    .toFlow()
                    .map {querySnapshot ->
                        querySnapshot.filter {
                            it.data.get("status") == VoteStatus.Active.value
                                    && it.data.get("talkId") == sessionId
                        }.map {
                            it.data.get("voteItemId") as String
                        }
                    }.collect {
                        emit(it)
                    }
        }
    }

    fun getTotalVotes(projectId: String, sessionId: String): Flow<Map<String, Long>> = flow {
        val user = getFirebaseUser()
        if (user != null) {
            firestore.collection("projects/$projectId/sessionVotes")
                    .toFlow()
                    .mapNotNull {
                        it.firstOrNull { it.id == sessionId }
                    }.collect {
                        emit(it.data as Map<String, Long>)
                    }
        }
    }

    suspend fun setVote(projectId: String, talkId: String, voteItemId: String, status: VoteStatus) = withFirebaseUser { firebaseUser ->
        val collectionReference = firestore.collection("projects/$projectId/userVotes")
        val querySnapshot = collectionReference
                .whereEqualTo("userId", firebaseUser.uid)
                .whereEqualTo("talkId", talkId)
                .whereEqualTo("voteItemId", voteItemId)
                .get()
                .await()

        if (querySnapshot.isEmpty) {
            val documentReference = collectionReference.document()
            documentReference.set(
                    mapOf("id" to documentReference.id,
                            "createdAt" to Date(),
                            "projectId" to projectId,
                            "status" to status,
                            "talkId" to talkId,
                            "updatedAt" to Date(),
                            "userId" to firebaseUser.uid,
                            "voteItemId" to voteItemId)
            )
        } else {
            if (querySnapshot.size() != 1) {
                Log.e(OpenFeedback::class.java.name, "Too many votes registered for ${firebaseUser.uid}")
            }

            val documentID = querySnapshot.documents.get(0).id
            collectionReference.document(documentID).update(
                    mapOf("updatedAt" to Date(),
                            "status" to status.value)
            )
        }
    }
}

fun Query.toFlow(): Flow<QuerySnapshot> = callbackFlow {
    val registration = addSnapshotListener { snapshot, exception ->
        if (snapshot != null) {
            offer(snapshot!!)
        }
        if (exception != null) {
            close(exception)
        }
    }

    awaitClose {
        registration.remove()
    }
}.conflate()

fun DocumentReference.toFlow(): Flow<DocumentSnapshot> = callbackFlow {
    val registration = addSnapshotListener { snapshot, exception ->
        if (snapshot != null) {
            offer(snapshot!!)
        }
        if (exception != null) {
            close(exception)
        }
    }

    awaitClose {
        registration.remove()
    }
}.conflate()
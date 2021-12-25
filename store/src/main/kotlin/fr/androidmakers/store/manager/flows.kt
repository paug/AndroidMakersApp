package fr.androidmakers.store.manager

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.cancellation.CancellationException

/**
 * Attach a snapshotListener to a [DocumentReference] and use it as a coroutine [Flow]
 * @param metadataChanges Indicates whether metadata-only changes
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun DocumentReference.toFlow(metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE) = callbackFlow {
    val listener = addSnapshotListener(metadataChanges) { value, error ->
        if (value != null) {
            trySendBlocking(value)
        }
        if (error != null) {
            cancel(CancellationException("Error getting DocumentReference snapshot", error))
        }
    }

    awaitClose { listener.remove() }
}

/**
 * Attach a snapshotListener to a [Query] and use it as a coroutine [Flow]
 * @param metadataChanges Indicates whether metadata-only changes
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun Query.toFlow(metadataChanges: MetadataChanges = MetadataChanges.EXCLUDE): Flow<QuerySnapshot> = callbackFlow {
    val listener = addSnapshotListener(metadataChanges) { value, error ->
        if (value != null) {
            trySendBlocking(value)
        }
        if (error != null) {
            cancel(CancellationException("Error getting Query snapshot", error))
        }
    }

    awaitClose { listener.remove() }
}
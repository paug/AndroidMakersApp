package fr.paug.androidmakers.util

import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

/**
 * Attach a snapshotListener to a [DocumentReference] and use it as a coroutine [Flow]
 * @param metadataChanges Indicates whether metadata-only changes
 */
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
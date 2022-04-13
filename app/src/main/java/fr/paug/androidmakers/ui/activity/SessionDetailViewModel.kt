package fr.paug.androidmakers.ui.activity

import androidx.lifecycle.ViewModel
import fr.paug.androidmakers.AndroidMakersApplication
import kotlinx.coroutines.flow.combine

class SessionDetailViewModel(
    val sessionId: String,
    val roomId: String,
    val startTimestamp: Long,
    val endTimestamp: Long,
) : ViewModel() {
    val sessionDetailState = combine(
        AndroidMakersApplication.instance().store.getSession(sessionId),
        AndroidMakersApplication.instance().store.getRoom(roomId)
    ) { session, room ->
        SessionDetailState.Loaded(session = session, room = room, startTimestamp = startTimestamp, endTimestamp = endTimestamp)
    }
}

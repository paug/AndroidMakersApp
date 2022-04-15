package fr.paug.androidmakers.ui.activity

import androidx.lifecycle.ViewModel
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.util.BookmarksStore
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull

class SessionDetailViewModel(
    private val sessionId: String,
    roomId: String,
    private val startTimestamp: Long,
    private val endTimestamp: Long,
) : ViewModel() {
  val sessionDetailState = combine(
      AndroidMakersApplication.instance().store.getSession(sessionId),
      AndroidMakersApplication.instance().store.getRoom(roomId),
      BookmarksStore.subscribe(sessionId),
  ) { session, room, isBookmarked ->
    SessionDetailState.Loaded(
        session = session,
        room = room,
        speakers = getSpeakers(session),
        startTimestamp = startTimestamp,
        endTimestamp = endTimestamp,
        isBookmarked = isBookmarked,
    )
  }

  private suspend fun getSpeakers(session: Session): List<Speaker> {
    val allSpeakers = AndroidMakersApplication.instance().store.getSpeakers().firstOrNull() ?: return emptyList()
    val allSpeakersById = allSpeakers.associateBy { it.id }
    return session.speakers.mapNotNull { allSpeakersById[it] }
  }

  fun bookmark(bookmarked: Boolean) {
    BookmarksStore.setBookmarked(sessionId, bookmarked)
  }
}

package fr.paug.androidmakers.wear.ui.main

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

data class UISession(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val isBookmarked: Boolean,
) {
  val formattedDuration: String = session.duration.inWholeMinutes.toString() + " min"
}

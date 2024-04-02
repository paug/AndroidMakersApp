package fr.paug.androidmakers.wear.ui.session

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class UISession(
  val session: Session,
  val speakers: List<Speaker>,
  val room: Room,
  val isBookmarked: Boolean,
) {
  val formattedDuration: String = session.duration.inWholeMinutes.toString() + " min"

  val isOver: Boolean
    get() {
      val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
      return session.endsAt < now
    }

  val isOngoing: Boolean
    get() {
      val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
      return session.startsAt <= now && now <= session.endsAt
    }
}

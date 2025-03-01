package com.androidmakers.ui.agenda

import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.isAppClinic
import fr.androidmakers.domain.utils.eventTimeZone
import fr.androidmakers.domain.utils.formatMediumDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toInstant

fun agendaToDays(agenda: Agenda, favoriteSessions: Set<String>): List<DaySchedule> {
  val roomsById = agenda.rooms.associateBy { it.id }
  val speakersById = agenda.speakers.associateBy { it.id }
  return agenda.sessions
    .sortedBy { it.startsAt }
    .groupBy { it.startsAt.date }
    .map { (date, sessions) ->
      DaySchedule(
        title = date.formatMediumDate(),
        date = date,
        sessions = sessions
          .map { it.toUISession(roomsById, speakersById, it.id in favoriteSessions) }
      )
    }
}

fun Speaker.toUISpeaker(): UISession.Speaker {
  return UISession.Speaker(
    name = name.orEmpty()
  )
}

fun Session.toUISession(
  rooms: Map<String, Room>,
  speakers: Map<String, Speaker>,
  isFavorite: Boolean
): UISession {
  return UISession(
    id = id,
    title = title,
    startDate = startsAt.toInstant(eventTimeZone),
    endDate = endsAt.toInstant(eventTimeZone),
    language = language,
    roomId = roomId,
    room = rooms[roomId]?.name ?: "unknown",
    speakers = this.speakers.mapNotNull { speakers[it]?.toUISpeaker() },
    isServiceSession = isServiceSession,
    isFavorite = isFavorite,
    isAppClinic = isAppClinic()
  )
}


class DaySchedule(
  val title: String,
  val date: LocalDate,
  val sessions: List<UISession>
)

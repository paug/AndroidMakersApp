package com.androidmakers.ui.agenda

import androidx.compose.runtime.Composable
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.utils.eventTimeZone
import fr.androidmakers.domain.utils.formatMediumDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toInstant

@Composable
fun agendaToDays(agenda: Agenda, favoriteSessions: Set<String>): List<DaySchedule> {

  return agenda.sessions.values.groupBy { it.startsAt.date }
      .entries
      .map {
        DaySchedule(
            title = it.key.formatMediumDate(),
            date = it.key,
            sessions = it.value.sortedBy { it.startsAt }
                .map { it.toUISession(agenda.rooms, agenda.speakers, favoriteSessions.contains(it.id)) }
        )
      }
}

fun Speaker.toUISpeaker(): UISession.Speaker {
  return UISession.Speaker(
      name = name ?: ""
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
      room = rooms[roomId]!!.name,
      speakers = this.speakers.mapNotNull { speakers[it]?.toUISpeaker() },
      isServiceSession = isServiceSession,
      isFavorite = isFavorite
  )
}


class DaySchedule(
    val title: String,
    val date: LocalDate,
    val sessions: List<UISession>
)

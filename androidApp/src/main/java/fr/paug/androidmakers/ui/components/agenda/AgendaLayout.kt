package fr.paug.androidmakers.ui.components.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Room
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import at.asitplus.KmmResult
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.MR
import fr.paug.androidmakers.ui.components.AgendaLayoutViewModel
import fr.paug.androidmakers.ui.components.ButtonRefreshableLceLayout
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.SessionFilter
import fr.paug.androidmakers.ui.util.stringResource
import fr.paug.androidmakers.util.eventTimeZone
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.todayIn
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun AgendaLayout(
    agendaFilterDrawerState: DrawerState,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
) {
  val agendaLayoutViewModel = viewModel<AgendaLayoutViewModel>()
  val agendaLayoutState by agendaLayoutViewModel.state.collectAsState()

  ModalNavigationDrawer(
      drawerState = agendaFilterDrawerState,
      drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = MaterialTheme.colorScheme.background,
            drawerContentColor = MaterialTheme.colorScheme.onBackground,
            drawerShape = RectangleShape,
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {
          AgendaFilterDrawer(
              rooms = agendaLayoutState.rooms,
              sessionFilters = agendaLayoutState.sessionFilters,
              onFiltersChanged = agendaLayoutViewModel::onFiltersChanged
          )
        }
      },
      content = {
        AgendaPagerOrLoading(agendaLayoutState.sessionFilters, onSessionClick)
      }
  )
}

class AgendaPagerViewModel : LceViewModel<Agenda>() {
  override fun produce(): Flow<KmmResult<Agenda>> {
    return AndroidMakersApplication.instance().getAgendaUseCase()
  }
}

@Composable
private fun AgendaPagerOrLoading(
    sessionFilters: List<SessionFilter>,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
) {
  ButtonRefreshableLceLayout(viewModel<AgendaPagerViewModel>()) {
    val days = agendaToDays(it)

    AgendaPager(
        initialPageIndex = days.todayPageIndex(),
        days = days.map { it.title },
        filterList = sessionFilters,
        onSessionClicked = {
          onSessionClick(
              it.id,
              it.roomId,
              it.startDate.toEpochMilliseconds(),
              it.endDate.toEpochMilliseconds()
          )
        }
    )
  }
}

/** Returns the index of today's [DaySchedule] in `this`, or zero. */
private fun List<DaySchedule>.todayPageIndex(): Int {
  val today = Clock.System.todayIn(eventTimeZone)
  return withIndex().firstOrNull { it.value.date == today }?.index ?: 0
}

@Composable
private fun AgendaFilterDrawer(
    rooms: List<Room>,
    sessionFilters: List<SessionFilter>,
    onFiltersChanged: (List<SessionFilter>) -> Unit,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    HeaderItem(stringResource(MR.strings.filter))
    FilterItem(
        text = stringResource(MR.strings.bookmarked),
        imageVector = Icons.Rounded.Bookmark,
        checked = sessionFilters.any { it.type == SessionFilter.FilterType.BOOKMARK },
        onCheck = { checked ->
          val newSessionFilters = sessionFilters.toMutableList().apply {
            removeAll { it.type == SessionFilter.FilterType.BOOKMARK }
            if (checked) add(SessionFilter(SessionFilter.FilterType.BOOKMARK, ""))
          }
          onFiltersChanged(newSessionFilters)
        }
    )

    HeaderItem(stringResource(MR.strings.language))
    val french = "French"
    FilterItem(
        text = stringResource(MR.strings.french),
        language = french,
        checked = sessionFilters.any { it.type == SessionFilter.FilterType.LANGUAGE && it.value == french },
        onCheck = { checked ->
          val newSessionFilters = sessionFilters.toMutableList().apply {
            removeAll { it.type == SessionFilter.FilterType.LANGUAGE && it.value == french }
            if (checked) add(SessionFilter(SessionFilter.FilterType.LANGUAGE, french))
          }
          onFiltersChanged(newSessionFilters)

        }
    )
    val english = "English"
    FilterItem(
        text = stringResource(MR.strings.english),
        language = english,
        checked = sessionFilters.any { it.type == SessionFilter.FilterType.LANGUAGE && it.value == english },
        onCheck = { checked ->
          val newSessionFilters = sessionFilters.toMutableList().apply {
            removeAll { it.type == SessionFilter.FilterType.LANGUAGE && it.value == english }
            if (checked) add(SessionFilter(SessionFilter.FilterType.LANGUAGE, english))
          }
          onFiltersChanged(newSessionFilters)
        }
    )

    HeaderItem(stringResource(MR.strings.rooms))
    for (room in rooms) {
      FilterItem(
          text = room.name,
          imageVector = Icons.Rounded.Room,
          checked = sessionFilters.any { it.type == SessionFilter.FilterType.ROOM && it.value == room.id },
          onCheck = { checked ->
            val newSessionFilters = sessionFilters.toMutableList().apply {
              removeAll { it.type == SessionFilter.FilterType.ROOM && it.value == room.id }
              if (checked) add(SessionFilter(SessionFilter.FilterType.ROOM, room.id))
            }
            onFiltersChanged(newSessionFilters)
          }
      )
    }
  }
}

@Composable
private fun FilterItem(
    text: String,
    imageVector: ImageVector? = null,
    language: String? = null,
    checked: Boolean,
    onCheck: (checked: Boolean) -> Unit,
) {
  Row(
      modifier = Modifier
          .fillMaxWidth()
          .clickable {
            onCheck(!checked)
          },
      verticalAlignment = Alignment.CenterVertically,
  ) {
    val textLeftMargin = 48.dp
    if (imageVector != null) {
      Icon(
          modifier = Modifier.width(textLeftMargin),
          imageVector = imageVector,
          tint = MaterialTheme.colorScheme.onSurface,
          contentDescription = text
      )
    }
    if (language != null) {
      Text(
          modifier = Modifier.width(textLeftMargin),
          textAlign = TextAlign.Center,
          text = EmojiUtils.getLanguageInEmoji(language)!!,
          style = MaterialTheme.typography.bodyMedium
      )
    }
    Text(
        modifier = Modifier
            .weight(1f)
            .padding(start = if (imageVector == null && language == null) textLeftMargin else 0.dp),
        text = text
    )
    Checkbox(
        modifier = Modifier
            .width(48.dp)
            .height(48.dp),
        checked = checked,
        onCheckedChange = null
    )
  }
}

@Composable
private fun HeaderItem(text: String) {
  Text(
      modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
      text = text,
      style = MaterialTheme.typography.titleMedium
  )
}


@Preview
@Composable
private fun AgendaFilterDrawerPreview() {
  AgendaFilterDrawer(
      rooms = listOf(
          Room("", "Room 1"),
          Room("", "Room 2"),
          Room("", "Room 3"),
          Room("", "Room 4")
      ),
      sessionFilters = listOf(
          SessionFilter(type = SessionFilter.FilterType.BOOKMARK, value = "")
      ),
      onFiltersChanged = {}
  )
}


@Preview
@Composable
private fun AgendaLayoutPreview() {
  AgendaLayout(
      agendaFilterDrawerState = DrawerState(DrawerValue.Closed, confirmStateChange = { true }),
      onSessionClick = { _, _, _, _ -> }
  )
}

class DaySchedule(
    val title: String,
    val date: LocalDate,
    val sessions: List<UISession>
)

internal fun agendaToDays(agenda: Agenda): List<DaySchedule> {
  return agenda.sessions.values.groupBy { it.startsAt.date }
      .entries
      .map {
        DaySchedule(
            title = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(it.key.toJavaLocalDate()),
            date = it.key,
            sessions = it.value.sortedBy { it.startsAt }.map { it.toUISession(agenda.rooms, agenda.speakers) }
        )
      }
}


fun Session.toUISession(rooms: Map<String, Room>, speakers: Map<String, Speaker>): UISession {
  return UISession(
      id = id,
      title = title,
      startDate = startsAt.toInstant(eventTimeZone),
      endDate = endsAt.toInstant(eventTimeZone),
      language = language,
      roomId = roomId,
      room = rooms.get(roomId)!!.name,
      speakers = this.speakers.mapNotNull { speakers[it]?.toUISpeaker() },
      isServiceSession = isServiceSession,
  )
}

fun Speaker.toUISpeaker(): UISession.Speaker {
  return UISession.Speaker(
      name = name ?: ""
  )
}

package fr.paug.androidmakers.ui.components.agenda

import android.util.SparseArray
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.androidmakers.store.model.Agenda
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.adapter.DaySchedule
import fr.paug.androidmakers.ui.adapter.RoomSchedule
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.ui.components.AgendaLayoutViewModel
import fr.paug.androidmakers.ui.components.AgendaPager
import fr.paug.androidmakers.ui.components.ButtonRefreshableLceLayout
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.SessionFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.DateFormat
import java.util.Arrays
import java.util.Calendar

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
  override fun produce(): Flow<Result<Agenda>> {
    return AndroidMakersApplication.instance().store.getAgenda()
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
  val todayPageIndex = indexOfLast { (startDayInEpochMillis, _, _) ->
    System.currentTimeMillis() >= startDayInEpochMillis
  }
  return if (todayPageIndex < 0) 0 else todayPageIndex
}

@Composable
private fun AgendaFilterDrawer(
    rooms: List<Room>,
    sessionFilters: List<SessionFilter>,
    onFiltersChanged: (List<SessionFilter>) -> Unit,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    HeaderItem(R.string.filter)
    FilterItem(
        text = stringResource(R.string.bookmarked),
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

    HeaderItem(R.string.filter_language_header)
    val french = "French"
    FilterItem(
        text = stringResource(R.string.french),
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
        text = stringResource(R.string.english),
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

    HeaderItem(R.string.rooms)
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
private fun HeaderItem(text: Int) {
  Text(
      modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp),
      text = stringResource(text),
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


private fun getRoomScheduleForDay(
    itemByDayOfTheYear: SparseArray<DaySchedule>,
    calendar: Calendar,
    scheduleSlot: ScheduleSlot,
): MutableList<RoomSchedule> {

  calendar.timeInMillis = scheduleSlot.startDate.toInstant(TimeZone.UTC).toEpochMilliseconds()
  val dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000
  var daySchedule: DaySchedule? = itemByDayOfTheYear.get(dayIndex)
  if (daySchedule == null) {
    val roomSchedule = ArrayList<RoomSchedule>()
    val title = DateFormat.getDateInstance().format(calendar.time)
    daySchedule = DaySchedule(calendar.timeInMillis, title, roomSchedule)
    itemByDayOfTheYear.put(dayIndex, daySchedule)
    return roomSchedule
  } else {
    return daySchedule.roomSchedules
  }
}

private fun getItemsOrdered(itemByDayOfTheYear: SparseArray<DaySchedule>): List<DaySchedule> {
  val size = itemByDayOfTheYear.size()
  val keysSorted = IntArray(size)
  for (i in 0 until size) {
    keysSorted[i] = itemByDayOfTheYear.keyAt(i)
  }
  Arrays.sort(keysSorted)
  val items = ArrayList<DaySchedule>(size)
  for (key in keysSorted) {
    items.add(itemByDayOfTheYear.get(key))
  }
  return items
}

private fun getSpeakers(agenda: Agenda, sessionId: String): List<Speaker> {
  return agenda.sessions.get(sessionId)?.speakers?.mapNotNull { speakerId ->
    agenda.speakers.get(speakerId)
  } ?: emptyList()
}

internal fun agendaToDays(agenda: Agenda): List<DaySchedule> {
  val itemByDayOfTheYear = SparseArray<DaySchedule>()

  val calendar = Calendar.getInstance()
  val scheduleSlots = agenda.slots
  for (scheduleSlot in scheduleSlots) {
    val agendaScheduleSessions = getAgendaItems(
        itemByDayOfTheYear = itemByDayOfTheYear,
        calendar = calendar,
        scheduleSlot = scheduleSlot,
        rooms = agenda.rooms
    )

    val session = agenda.sessions.get(scheduleSlot.sessionId)
    if (session == null) {
      // this session has disappeared, skip it
      continue
    }
    agendaScheduleSessions.add(
        ScheduleSession(
            scheduleSlot,
            session.title,
            session.language,
            getSpeakers(agenda, scheduleSlot.sessionId)
        )
    )
  }

  return getItemsOrdered(itemByDayOfTheYear)
}

private fun getAgendaItems(
    itemByDayOfTheYear: SparseArray<DaySchedule>,
    calendar: Calendar,
    scheduleSlot: ScheduleSlot,
    rooms: Map<String, Room>,
): ArrayList<ScheduleSession> {
  val roomSchedules = getRoomScheduleForDay(itemByDayOfTheYear, calendar, scheduleSlot)
  var roomScheduleForThis: RoomSchedule? = null
  for (roomSchedule in roomSchedules) {
    if (roomSchedule.roomId == scheduleSlot.roomId) {
      roomScheduleForThis = roomSchedule
      break
    }
  }
  if (roomScheduleForThis == null) {
    val agendaScheduleSessions = ArrayList<ScheduleSession>()
    val room = rooms.get(scheduleSlot.roomId)!!
    val titleRoom = room.name
    roomScheduleForThis = RoomSchedule(scheduleSlot.roomId, titleRoom, agendaScheduleSessions)
    roomSchedules.add(roomScheduleForThis)
    roomSchedules.sort()
    return agendaScheduleSessions
  } else {
    return roomScheduleForThis.scheduleSessions
  }
}

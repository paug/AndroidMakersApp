package com.androidmakers.ui.agenda

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
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.common.ButtonRefreshableLceLayout
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.SessionFilter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.utils.eventTimeZone
import fr.paug.androidmakers.ui.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AgendaLayout(
    agendaFilterDrawerState: DrawerState,
    onSessionClick: (sessionId: String) -> Unit,
) {
  val agendaLayoutViewModel = koinViewModel<AgendaLayoutViewModel>()
  val agendaLayoutState by agendaLayoutViewModel.state.collectAsState()

  ModalNavigationDrawer(
      drawerState = agendaFilterDrawerState,
      drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = MaterialTheme.colorScheme.surface,
            drawerContentColor = MaterialTheme.colorScheme.onSurface,
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

@Composable
private fun AgendaPagerOrLoading(
    sessionFilters: List<SessionFilter>,
    onSessionClick: (sessionId: String) -> Unit,
) {
  val viewModel = koinViewModel<AgendaPagerViewModel>()
  ButtonRefreshableLceLayout(viewModel) { daySchedules ->
    AgendaPager(
        initialPageIndex = daySchedules.todayPageIndex(),
        days = daySchedules.map { it.title },
        filterList = sessionFilters,
        onSessionClicked = {
          onSessionClick(it.id)
        }
    )
  }
}

/** Returns the index of today's [DaySchedule] in `this`, or zero. */
private fun List<DaySchedule>.todayPageIndex(): Int {
  val today = Clock.System.todayIn(eventTimeZone)
  return indexOfFirst { it.date == today }.coerceAtLeast(0)
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
      onSessionClick = { _ -> }
  )
}

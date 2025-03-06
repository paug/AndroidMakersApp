package com.androidmakers.ui.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.LceLayout
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.getPlatformContext
import com.androidmakers.ui.model.AgendaState
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UISession
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
    viewModel: AgendaViewModel = koinViewModel()
) {
  val rooms by viewModel.rooms.collectAsState(emptyList())
  val sessionFilters by viewModel.sessionFilters.collectAsState()
  val uiStateLce by viewModel.values.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()

  ModalNavigationDrawer(
      drawerState = agendaFilterDrawerState,
      drawerContent = {
        ModalDrawerSheet(
            drawerContainerColor = MaterialTheme.colorScheme.surface,
            drawerContentColor = MaterialTheme.colorScheme.onSurface,
            drawerShape = RectangleShape,
        ) {
          AgendaFilterDrawer(
              rooms = rooms,
              sessionFilters = sessionFilters,
              onFiltersChanged = viewModel::onFiltersChanged
          )
        }
      },
      content = {
        val context = getPlatformContext()

        AgendaPagerOrLoading(
          uiStateLce = uiStateLce,
          isRefreshing = isRefreshing,
          onRefresh = viewModel::refresh,
          sessionFilters = sessionFilters,
          onSessionClick = { onSessionClick(it.id) },
          onApplyForAppClinicClick = { viewModel.applyForAppClinic(context) },
          onSessionBookmark = viewModel::setSessionBookmark,
        )
      }
  )
}

@Composable
private fun AgendaPagerOrLoading(
  uiStateLce: Lce<AgendaState>,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  sessionFilters: Set<SessionFilter>,
  onSessionClick: (UISession) -> Unit,
  onApplyForAppClinicClick: () -> Unit,
  onSessionBookmark: (UISession, Boolean) -> Unit
) {
  LceLayout(
    lce = uiStateLce,
    isRefreshing = isRefreshing,
    onRetry = onRefresh
  ) { uiState ->
    val days = uiState.days
    AgendaPager(
        days = days,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        initialPageIndex = days.todayPageIndex(),
        sessionFilters = sessionFilters,
        onSessionClick = onSessionClick,
        onApplyForAppClinicClick = onApplyForAppClinicClick,
        onSessionBookmark = onSessionBookmark
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
    sessionFilters: Set<SessionFilter>,
    onFiltersChanged: (Set<SessionFilter>) -> Unit,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    HeaderItem(stringResource(MR.strings.filter))
    FilterItem(
      filter = SessionFilter.Bookmark,
      filters = sessionFilters,
      text = stringResource(MR.strings.bookmarked),
      imageVector = Icons.Rounded.Bookmark,
      onFiltersChanged = onFiltersChanged
    )

    HeaderItem(stringResource(MR.strings.language))
    FilterItem(
      filter = SessionFilter.Language(SessionFilter.Language.FRENCH),
      filters = sessionFilters,
      text = stringResource(MR.strings.french),
      language = SessionFilter.Language.FRENCH,
      onFiltersChanged = onFiltersChanged
    )
    FilterItem(
      filter = SessionFilter.Language(SessionFilter.Language.ENGLISH),
      filters = sessionFilters,
      text = stringResource(MR.strings.english),
      language = SessionFilter.Language.ENGLISH,
      onFiltersChanged = onFiltersChanged
    )

    HeaderItem(stringResource(MR.strings.rooms))
    for (room in rooms) {
      FilterItem(
        filter = SessionFilter.Room(room.id),
        filters = sessionFilters,
        text = room.name,
        imageVector = Icons.Rounded.Room,
        onFiltersChanged = onFiltersChanged
      )
    }
  }
}

@Composable
private fun FilterItem(
    filter: SessionFilter,
    filters: Set<SessionFilter>,
    text: String,
    imageVector: ImageVector? = null,
    language: String? = null,
    onFiltersChanged: (Set<SessionFilter>) -> Unit,
) {
  val checked = filter in filters
  Row(
      modifier = Modifier
          .fillMaxWidth()
          .clickable {
            val newSessionFilters = if (checked) {
              filters - filter
            } else {
              filters + filter
            }
            onFiltersChanged(newSessionFilters)
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
      sessionFilters = setOf(SessionFilter.Bookmark),
      onFiltersChanged = {}
  )
}


@Preview
@Composable
private fun AgendaLayoutPreview() {
  AgendaLayout(
      agendaFilterDrawerState = DrawerState(DrawerValue.Closed),
      onSessionClick = { _ -> }
  )
}

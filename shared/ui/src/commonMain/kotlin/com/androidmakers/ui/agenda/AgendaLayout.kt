package com.androidmakers.ui.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.LceLayout
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.model.AgendaState
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.utils.eventTimeZone
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.bookmarked
import fr.paug.androidmakers.ui.english
import fr.paug.androidmakers.ui.filter
import fr.paug.androidmakers.ui.french
import fr.paug.androidmakers.ui.language
import fr.paug.androidmakers.ui.rooms
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaLayout(
    showFilterBottomSheet: Boolean,
    onFilterBottomSheetDismiss: () -> Unit,
    onSessionClick: (sessionId: String) -> Unit,
    viewModel: AgendaViewModel = koinViewModel()
) {
  val rooms by viewModel.rooms.collectAsStateWithLifecycle(emptyList())
  val sessionFilters by viewModel.sessionFilters.collectAsStateWithLifecycle()
  val uiStateLce by viewModel.values.collectAsStateWithLifecycle()
  val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

  val urlOpener = LocalUriHandler.current.toUrlOpener()

  AgendaPagerOrLoading(
    uiStateLce = uiStateLce,
    isRefreshing = isRefreshing,
    onRefresh = viewModel::refresh,
    sessionFilters = sessionFilters,
    onSessionClick = { onSessionClick(it.id) },
    onApplyForAppClinicClick = { viewModel.applyForAppClinic(urlOpener) },
    onSessionBookmark = viewModel::setSessionBookmark,
  )

  if (showFilterBottomSheet) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onFilterBottomSheetDismiss,
        sheetState = sheetState,
    ) {
      AgendaFilterDrawer(
          rooms = rooms,
          sessionFilters = sessionFilters,
          onFiltersChanged = viewModel::onFiltersChanged
      )
    }
  }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AgendaFilterDrawer(
    rooms: List<Room>,
    sessionFilters: Set<SessionFilter>,
    onFiltersChanged: (Set<SessionFilter>) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .verticalScroll(state = rememberScrollState())
      .padding(horizontal = 16.dp)
      .padding(bottom = 24.dp)
  ) {
    // Title row
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(Res.string.filter),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.weight(1f),
      )
      if (sessionFilters.isNotEmpty()) {
        TextButton(onClick = { onFiltersChanged(emptySet()) }) {
          Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = null,
            modifier = Modifier.padding(end = 4.dp),
          )
          Text("Clear")
        }
      }
    }

    // Bookmark
    FilterSection(title = null) {
      FilterChip(
        selected = SessionFilter.Bookmark in sessionFilters,
        onClick = { onFiltersChanged(sessionFilters.toggle(SessionFilter.Bookmark)) },
        label = { Text(stringResource(Res.string.bookmarked)) },
        leadingIcon = {
          Icon(
            imageVector = Icons.Rounded.Bookmark,
            contentDescription = null,
          )
        },
      )
    }

    // Language
    FilterSection(title = stringResource(Res.string.language)) {
      val french = SessionFilter.Language(SessionFilter.Language.FRENCH)
      FilterChip(
        selected = french in sessionFilters,
        onClick = { onFiltersChanged(sessionFilters.toggle(french)) },
        label = {
          Text("${EmojiUtils.getLanguageInEmoji(SessionFilter.Language.FRENCH)} ${stringResource(Res.string.french)}")
        },
      )
      val english = SessionFilter.Language(SessionFilter.Language.ENGLISH)
      FilterChip(
        selected = english in sessionFilters,
        onClick = { onFiltersChanged(sessionFilters.toggle(english)) },
        label = {
          Text("${EmojiUtils.getLanguageInEmoji(SessionFilter.Language.ENGLISH)} ${stringResource(Res.string.english)}")
        },
      )
    }

    // Rooms
    FilterSection(title = stringResource(Res.string.rooms)) {
      for (room in rooms) {
        val filter = SessionFilter.Room(room.id)
        FilterChip(
          selected = filter in sessionFilters,
          onClick = { onFiltersChanged(sessionFilters.toggle(filter)) },
          label = { Text(room.name) },
        )
      }
    }
  }
}

private fun Set<SessionFilter>.toggle(filter: SessionFilter): Set<SessionFilter> {
  return if (filter in this) this - filter else this + filter
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterSection(
    title: String?,
    content: @Composable () -> Unit,
) {
  if (title != null) {
    Spacer(modifier = Modifier.padding(top = 8.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.titleMedium,
    )
  }
  FlowRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier.padding(top = 8.dp),
  ) {
    content()
  }
}

@Preview
@Composable
private fun AgendaFilterDrawerPreview() {
  AgendaFilterDrawer(
      rooms = listOf(
          Room("", "Moebius"),
          Room("", "Blin"),
          Room("", "202"),
          Room("", "BoF")
      ),
      sessionFilters = setOf(
          SessionFilter.Bookmark,
          SessionFilter.Language(SessionFilter.Language.FRENCH)
      ),
      onFiltersChanged = {}
  )
}


@Preview
@Composable
private fun AgendaLayoutPreview() {
  AgendaLayout(
      showFilterBottomSheet = false,
      onFilterBottomSheetDismiss = {},
      onSessionClick = { _ -> }
  )
}

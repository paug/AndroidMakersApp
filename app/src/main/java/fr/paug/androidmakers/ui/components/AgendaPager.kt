package fr.paug.androidmakers.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import fr.androidmakers.store.model.Venue
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.activity.VenueLayout
import fr.paug.androidmakers.ui.adapter.DaySchedule
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.model.UIVenue
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.TimeUtils
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.*



@OptIn(ExperimentalPagerApi::class)
@Composable
fun AgendaPager(
    days: List<DaySchedule>,
    onSessionClicked: (UISession) -> Unit,
    filterList: List<SessionFilter>
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    val pagerState = rememberPagerState()


    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
          TabRowDefaults.Indicator(
              Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
          )
        }
    ) {
      repeat(days.size) {
        val coroutineScope = rememberCoroutineScope()

        Tab(
            text = { Text(days[it].title) },
            selected = pagerState.currentPage == it,
            onClick = {
              coroutineScope.launch {
                pagerState.animateScrollToPage(it)
              }
            },
        )
      }
    }

    HorizontalPager(
        count = days.size,
        state = pagerState,
    ) { page ->
      val items = days[page].roomSchedules.flatMap { it.scheduleSessions }
          .filterSessions(filterList)
          .sorted()
          .map { item ->
            UISession(
                id = item.sessionId,
                title = item.title,
                language = item.language,
                startDate = OffsetDateTime.parse(item.slot.startDate).toInstant(),
                endDate = OffsetDateTime.parse(item.slot.endDate).toInstant(),
                room = getRoomTitle(item, days[page]),
                roomId = item.roomId,
                speakers = item.speakers.map {
                  UISession.Speaker(it.name ?: "")
                },
            )
          }

      AgendaColumn(
          sessionsPerStartTime = addSeparators(LocalContext.current, items),
          onSessionClicked = onSessionClicked
      )
    }
  }
}

private fun addSeparators(context: Context, sessions: List<UISession>): Map<String, List<UISession>> {
  return sessions.map {
    TimeUtils.formatShortTime(context, Date(it.startDate.toEpochMilli())) to it
  }
      .groupBy(
          keySelector =  { it.first }
      ) {
        it.second
      }
}


private fun List<ScheduleSession>.filterSessions(
    filterList: List<SessionFilter>
): List<ScheduleSession> {
  val filteredSessions = mutableListOf<ScheduleSession>()

  if (filterList.isEmpty()) {
    filteredSessions.addAll(this)
  } else {
    for (item in this) {
      for (sessionFilter in filterList) {
        val matched = when (sessionFilter.type) {
          SessionFilter.FilterType.BOOKMARK -> {
            SessionSelector.isSelected(item.sessionId)
          }
          SessionFilter.FilterType.LANGUAGE -> {
            sessionFilter.value == item.language
          }
          SessionFilter.FilterType.ROOM -> {
            sessionFilter.value == item.roomId
          }
        }

        if (matched) {
          filteredSessions.add(item)
        }
      }
    }
  }

  return filteredSessions
}

private fun getRoomTitle(scheduleSession: ScheduleSession, daySchedule: DaySchedule): String {
  var roomTitle = ""
  for (roomSchedule in daySchedule.roomSchedules) {
    if (roomSchedule.roomId == scheduleSession.roomId) {
      roomTitle = roomSchedule.title
    }
  }
  return roomTitle
}
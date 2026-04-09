package com.androidmakers.ui.agenda

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.androidmakers.ui.common.EmptyLayout
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.utils.eventTimeZone
import fr.androidmakers.domain.utils.formatShortTime
import kotlin.time.Clock
import kotlinx.coroutines.launch
import kotlinx.datetime.todayIn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaPager(
    days: List<DaySchedule>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    initialPageIndex: Int,
    sessionFilters: Set<SessionFilter>,
    onSessionClick: (UISession) -> Unit,
    onApplyForAppClinicClick: () -> Unit,
    onSessionBookmark: (UISession, Boolean) -> Unit
) {
  Column(
      modifier = Modifier.fillMaxWidth()
  ) {

    val pagerState = rememberPagerState(
      initialPage = initialPageIndex,
      pageCount = { days.size }
    )

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
      days.forEachIndexed { index, day ->
        val coroutineScope = rememberCoroutineScope()
        Tab(
          text = {
            Text(day.title)
          },
          selected = pagerState.currentPage == index,
          selectedContentColor = MaterialTheme.colorScheme.onSurface,
          unselectedContentColor = MaterialTheme.colorScheme.onSurface,
          onClick = {
            coroutineScope.launch {
              pagerState.animateScrollToPage(index)
            }
          }
        )
      }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
      val pullRefreshState = rememberPullToRefreshState()
      val day = days[page]
      val sessionsPerStartTime = remember(day, sessionFilters) {
        day.sessions
          .filter(sessionFilters)
          .groupBy { it.startDate.formatShortTime() }
      }

      PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullRefreshState
      ) {
        val isToday = remember(page) {
          days[page].date == Clock.System.todayIn(eventTimeZone)
        }
        val initialScrollIndex = remember(sessionsPerStartTime, isToday) {
          if (isToday) sessionsPerStartTime.currentTimeScrollIndex() else 0
        }
        if (sessionsPerStartTime.isEmpty()) {
          EmptyLayout()
        } else {
          AgendaColumn(
              sessionsPerStartTime = sessionsPerStartTime,
              initialFirstVisibleItemIndex = initialScrollIndex,
              onSessionClick = onSessionClick,
              onApplyForAppClinicClick = onApplyForAppClinicClick,
              onSessionBookmark = onSessionBookmark
          )
        }
      }
    }
  }
}

// algorithm to filter sessions by applying filters, if the filters is same category we keep
// the combined logic (OR) otherwise it's AND with category filters
// example: Language English && (Rooms Moebius || Rooms A...)
// the algorithm is inspired by Inverted index
// time complexity is O(n * m) where n is the number of sessions and m is the number of filters
private fun List<UISession>.filter(
    filters: Collection<SessionFilter>
): List<UISession> {
  if (filters.isEmpty()) {
    return this
  }

  val filtersByType = filters.groupBy { it::class }.values

  return filter { session ->
    // Match if at least one filter of each type matches
    filtersByType.all { filter ->
      filter.any { it.matches(session) }
    }
  }
}

/**
 * Computes the lazy list item index of the sticky header for the last time slot
 * that has already started (i.e., whose start time <= now). Returns 0 if all
 * slots are in the future. The lazy list structure is:
 *   index 0              : sticky header for slot 0
 *   index 1..n           : sessions of slot 0
 *   index n+1            : sticky header for slot 1
 *   ...
 */
private fun Map<String, List<UISession>>.currentTimeScrollIndex(): Int {
  val now = Clock.System.now()
  var itemIndex = 0
  var targetIndex = 0

  for ((_, sessions) in this) {
    val slotStart = sessions.firstOrNull()?.startDate
    if (slotStart != null && slotStart <= now) {
      targetIndex = itemIndex
    }
    itemIndex += 1 + sessions.size // 1 for sticky header + N for sessions
  }

  return targetIndex
}

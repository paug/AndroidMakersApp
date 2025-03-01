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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.androidmakers.ui.common.EmptyLayout
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.utils.formatShortTime
import kotlinx.coroutines.launch

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

      PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullRefreshState
      ) {
        val sessions = days[page].sessions.filter(sessionFilters)
        if (sessions.isEmpty()) {
          EmptyLayout()
        } else {
          AgendaColumn(
              sessionsPerStartTime = sessions.groupBy { it.startDate.formatShortTime() },
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

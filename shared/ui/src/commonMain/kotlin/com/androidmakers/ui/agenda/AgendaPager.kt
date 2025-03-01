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
    filterList: List<SessionFilter>,
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
        val sessions = days[page].sessions.filter(filterList)
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
    filterList: List<SessionFilter>
): List<UISession> {
  if (filterList.isEmpty()) {
    return this
  }

  val sessionsByFilterType =
      mutableMapOf<SessionFilter.FilterType, MutableList<UISession>>()
  for (filter in filterList) {
    if (!sessionsByFilterType.containsKey(filter.type)) {
      sessionsByFilterType[filter.type] = mutableListOf()
    }
  }
  for (session in this) {
    for (filter in filterList) {
      when (filter.type) {
        SessionFilter.FilterType.BOOKMARK -> {
          val bookmarked = session.isFavorite
          if (bookmarked) {
            sessionsByFilterType[filter.type]?.add(session)
          }
        }

        SessionFilter.FilterType.LANGUAGE -> {
          if (filter.value == session.language) {
            sessionsByFilterType[filter.type]?.add(session)
          }
        }

        SessionFilter.FilterType.ROOM -> {
          if (filter.value == session.roomId) {
            sessionsByFilterType[filter.type]?.add(session)
          }
        }
      }
    }
  }

  //get union join of all ScheduleSessions
  val origin = sessionsByFilterType.values.flatten().toMutableSet()
  sessionsByFilterType.values.forEach { origin.retainAll(it) }
  return origin.sortedBy { it.startDate }
}

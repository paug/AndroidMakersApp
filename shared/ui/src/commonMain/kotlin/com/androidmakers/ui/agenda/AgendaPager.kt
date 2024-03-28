package com.androidmakers.ui.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.common.EmptyLayout
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.common.SwipeRefreshableLceLayout
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.utils.formatShortTime
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaPager(
    initialPageIndex: Int,
    days: List<String>,
    onSessionClicked: (UISession) -> Unit,
    filterList: List<SessionFilter>
) {
  Column(
      modifier = Modifier.fillMaxWidth()
  ) {

    val pagerState = rememberPagerState(
        pageCount = { days.size }, initialPage = initialPageIndex)

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
      repeat(days.size) {
        val coroutineScope = rememberCoroutineScope()

        Tab(
            text = {
              Text(days[it])
            },
            selected = pagerState.currentPage == it,
            selectedContentColor = MaterialTheme.colorScheme.onSurface,
            unselectedContentColor = MaterialTheme.colorScheme.onSurface,
            onClick = {
              coroutineScope.launch {
                pagerState.animateScrollToPage(it)
              }
            },

            )
      }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
      val viewModel = koinViewModel(vmClass = AgendaPagerViewModel::class)
      val favoriteSessions by viewModel.getFavoriteSessions().collectAsState(emptySet())
      SwipeRefreshableLceLayout(viewModel = viewModel) {
        val days = agendaToDays(it, favoriteSessions)
        val items = days[page].sessions.filter(filterList)
        if (items.isEmpty()) {
          EmptyLayout()
        } else {
          items.filter { favoriteSessions.contains(it.id) }.forEach {
              it.isFavorite = true
          }
          AgendaColumn(
              sessionsPerStartTime = addSeparators(items),
              onSessionClicked = onSessionClicked,
              onSessionBookmarked = { uiSession, bookmarked ->
                viewModel.setSessionBookmark(uiSession, bookmarked)
              }
          )
        }
      }
    }
  }
}

private fun addSeparators(
    sessions: List<UISession>
): Map<String, List<UISession>> {
  return sessions.map {
    it.startDate.formatShortTime() to it
  }
      .groupBy(
          keySelector = { it.first }
      ) {
        it.second
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

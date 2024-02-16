package fr.paug.androidmakers.ui.components.agenda

import android.content.Context
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.paug.androidmakers.ui.components.EmptyLayout
import fr.paug.androidmakers.ui.components.SwipeRefreshableLceLayout
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.util.BookmarksStore
import fr.paug.androidmakers.util.SessionFilter
import fr.paug.androidmakers.util.TimeUtils
import kotlinx.coroutines.launch
import java.util.*


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
              Text(
                  text = days[it],
                  style = MaterialTheme.typography.headlineSmall.copy(
                      color = MaterialTheme.colorScheme.primary,
                      fontSize = 20.sp
                  )
              )
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
      val viewModel = viewModel<AgendaPagerViewModel>()
      SwipeRefreshableLceLayout(viewModel = viewModel) {
        val days = agendaToDays(it)
        val items = days[page].sessions.filter(filterList)
        if (items.isEmpty()) {
          EmptyLayout()
        } else {
          AgendaColumn(
              sessionsPerStartTime = addSeparators(LocalContext.current, items),
              onSessionClicked = onSessionClicked
          )
        }
      }
    }
  }
}

private fun addSeparators(
    context: Context,
    sessions: List<UISession>
): Map<String, List<UISession>> {
  return sessions.map {
    TimeUtils.formatShortTime(context, Date(it.startDate.toEpochMilliseconds())) to it
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
          if (BookmarksStore.isBookmarked(session.id)) {
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

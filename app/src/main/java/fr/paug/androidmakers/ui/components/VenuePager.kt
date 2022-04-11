package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.activity.VenueLayout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VenuePager() {
  Column(modifier = Modifier.fillMaxWidth()) {
    val pagerState = rememberPagerState()

    val titles = listOf(
        R.string.venue_conference_tab,
        R.string.venue_afterparty_tab,
        R.string.venue_floor_plan_tab
    )

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
      repeat(titles.size) {
        val coroutineScope = rememberCoroutineScope()

        Tab(
            text = { Text(stringResource(id = titles[it])) },
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
        count = titles.size,
        state = pagerState,
    ) { page ->
      when(page) {
        0 -> {
          VenueLayout()
        }
      }
    }
  }
}
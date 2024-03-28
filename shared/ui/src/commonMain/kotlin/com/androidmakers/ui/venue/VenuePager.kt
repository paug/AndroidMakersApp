package com.androidmakers.ui.venue

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.common.LceLayout
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UIVenue
import com.androidmakers.ui.model.toLce
import dev.icerock.moko.resources.compose.stringResource
import fr.paug.androidmakers.ui.MR
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VenuePager() {
  val viewModel = koinViewModel(VenueViewModel::class)

  Column(modifier = Modifier.fillMaxWidth()) {

    val titles = listOf(
        MR.strings.venue_conference_tab,
        MR.strings.venue_afterparty_tab,
        MR.strings.venue_floor_plan_tab
    )

    val pagerState = rememberPagerState(pageCount = { titles.size })

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
      repeat(titles.size) {
        val coroutineScope = rememberCoroutineScope()

        Tab(
            text = {
              Text(
                  text = stringResource(titles[it]),
              )
            },
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
        state = pagerState,
    ) { page ->
      when (page) {
        0, 1 -> {

          val flow = remember(page) {
            if (page == 0) {
              viewModel.getConferenceVenueUseCase()
            } else {
              viewModel.getAfterpartyVenueUseCase()
            }.map {
              it.toLce()
            }
          }
          val venueState = flow.collectAsState(initial = Lce.Loading)

          LceLayout(lce = venueState.value) { venue ->
            val uiVenue = UIVenue(
                imageUrl = venue.imageUrl,
                descriptionEn = venue.description,
                descriptionFr = venue.descriptionFr,
                address = venue.address,
                name = venue.name,
                coordinates = venue.coordinates,
            )
            VenueLayout(
                uiVenue = uiVenue,
                onClickOnMap = {
                  viewModel.openMapUseCase(uiVenue.coordinates ?: "", uiVenue.name)
                }
            )
          }
        }

        else -> {
          FloorPlan()
        }
      }
    }
  }
}

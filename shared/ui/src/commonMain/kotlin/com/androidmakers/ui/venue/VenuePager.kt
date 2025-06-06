package com.androidmakers.ui.venue

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
import androidx.compose.ui.text.style.TextOverflow
import com.androidmakers.ui.common.LceLayout
import com.androidmakers.ui.getPlatformContext
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UIVenue
import com.androidmakers.ui.model.toLce
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.venue_afterparty_tab
import fr.paug.androidmakers.ui.venue_conference_tab
import fr.paug.androidmakers.ui.venue_floor_plan_tab
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VenuePager() {
  val viewModel = koinViewModel<VenueViewModel>()

  Column(modifier = Modifier.fillMaxWidth()) {

    val titles = listOf(
      Res.string.venue_conference_tab,
      Res.string.venue_afterparty_tab,
      Res.string.venue_floor_plan_tab
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
              overflow = TextOverflow.Ellipsis,
              maxLines = 1
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
            val context = getPlatformContext()
            VenueLayout(
              uiVenue = uiVenue,
              onClickOnMap = {
                viewModel.openMapUseCase(context, uiVenue.coordinates.orEmpty(), uiVenue.name)
              }
            )
          }
        }

        else -> {
          val flow = remember(page) {
            viewModel.getConferenceVenueUseCase()
          }.map {
            it.toLce()
          }
          val venueState = flow.collectAsState(initial = Lce.Loading)
          LceLayout(lce = venueState.value) { venue ->
            FloorPlan(venue.floorPlanUrl.orEmpty())
          }
        }
      }
    }
  }
}

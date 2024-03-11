package fr.paug.androidmakers.ui.components

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
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.MR
import fr.paug.androidmakers.ui.components.venue.VenueLayout
import fr.paug.androidmakers.ui.model.UIVenue
import fr.paug.androidmakers.ui.util.stringResource
import fr.paug.androidmakers.ui.viewmodel.Lce
import fr.paug.androidmakers.ui.viewmodel.toLce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VenuePager() {
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
                  style = MaterialTheme.typography.headlineSmall.copy(
                      color = MaterialTheme.colorScheme.primary,
                      fontSize = 18.sp
                  )
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
              AndroidMakersApplication.instance().getConferenceVenueUseCase()
            } else {
              AndroidMakersApplication.instance().getAfterpartyVenueUseCase()
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
            VenueLayout(uiVenue)
          }
        }

        else -> {
          FloorPlan()
        }
      }
    }
  }
}

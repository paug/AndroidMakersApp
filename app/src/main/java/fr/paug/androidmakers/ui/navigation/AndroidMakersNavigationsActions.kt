package fr.paug.androidmakers.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import fr.paug.androidmakers.R

object AndroidMakersRoute {
  const val ROOT_ROUTE = "root_route"

  const val AGENDA_ROUTE = "agenda_route"
  const val AGENDA_LIST = "agenda_list"
  const val AGENDA_DETAILS = "agenda_details/{sessionId}/{roomId}/{startTime}/{endTime}"

  const val VENUE_ROUTE = "venue_route"
  const val VENUE = "venue"

  const val SPEAKERS_ROUTE = "speakers_route"
  const val SPEAKERS_LIST = "speakers_list"
  const val SPEAKER_DETAILS = "speaker_details/{speakerId}"

  const val SPONSORS_ROUTE = "sponsors_route"
  const val SPONSORS = "sponsors"

  const val ABOUT_ROUTE = "about_route"
  const val ABOUT = "about"
}

data class AndroidMakersTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class AndroidMakersNavigationActions(private val navController: NavHostController) {

  fun navigateTo(destination: AndroidMakersTopLevelDestination) {
    navController.navigate(destination.route) {
      // Pop up to the start destination of the graph to
      // avoid building up a large stack of destinations
      // on the back stack as users select items
      popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
      }
      // Avoid multiple copies of the same destination when re-selecting the same item
      launchSingleTop = true
      // Restore state when re-selecting a previously selected item
      restoreState = true
    }
  }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    AndroidMakersTopLevelDestination(
        route = AndroidMakersRoute.AGENDA_ROUTE,
        selectedIcon = Icons.Rounded.CalendarMonth,
        unselectedIcon = Icons.Rounded.CalendarMonth,
        iconTextId = R.string.title_agenda
    ),
    AndroidMakersTopLevelDestination(
        route = AndroidMakersRoute.VENUE_ROUTE,
        selectedIcon = Icons.Rounded.LocationCity,
        unselectedIcon = Icons.Rounded.LocationCity,
        iconTextId = R.string.title_venue
    ),
    AndroidMakersTopLevelDestination(
        route = AndroidMakersRoute.SPEAKERS_ROUTE,
        selectedIcon = Icons.Rounded.Groups,
        unselectedIcon = Icons.Rounded.Groups,
        iconTextId = R.string.title_speakers
    ),
    AndroidMakersTopLevelDestination(
        route = AndroidMakersRoute.SPONSORS_ROUTE,
        selectedIcon = Icons.Rounded.Diamond,
        unselectedIcon = Icons.Rounded.Diamond,
        iconTextId = R.string.title_sponsors
    ),
    AndroidMakersTopLevelDestination(
        route = AndroidMakersRoute.ABOUT_ROUTE,
        selectedIcon = Icons.Rounded.Info,
        unselectedIcon = Icons.Rounded.Info,
        iconTextId = R.string.title_about
    )
)
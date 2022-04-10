package fr.paug.androidmakers.ui.activity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.navigation.AVANavigationRoute

/**
 * AVA stands for Agenda/Venue/About.
 *
 * This layout contains the bottom bar, and the selected layout.
 */
@Composable
fun AVALayout(
    onSessionClick: (sessionId: String) -> Unit
) {
    val avaNavController = rememberNavController()
    val navBackStackEntry by avaNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme { // TODO replace with app's Theme
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        if (currentRoute == AVANavigationRoute.AGENDA.name) {
                            // TODO propagate up the click event
                            var filterMenuOpened by remember { mutableStateOf(false) }
                            IconButton(onClick = { filterMenuOpened = true }) {
                                Icon(painter = painterResource(R.drawable.ic_filter_list_white_24dp), contentDescription = stringResource(R.string.filter))
                            }
                        }
                    }
                )
            },

            bottomBar = {
                BottomNavigation {
                    BottomNavigationItem(
                        avaNavController = avaNavController,
                        iconResId = R.drawable.ic_event_note_white_24dp,
                        labelResId = R.string.title_agenda,
                        currentRoute = currentRoute,
                        destinationRoute = AVANavigationRoute.AGENDA
                    )
                    BottomNavigationItem(
                        avaNavController = avaNavController,
                        iconResId = R.drawable.ic_location_city_black_24dp,
                        labelResId = R.string.title_venue,
                        currentRoute = currentRoute,
                        destinationRoute = AVANavigationRoute.VENUE
                    )
                    BottomNavigationItem(
                        avaNavController = avaNavController,
                        iconResId = R.drawable.ic_info_outline_black_24dp,
                        labelResId = R.string.title_about,
                        currentRoute = currentRoute,
                        destinationRoute = AVANavigationRoute.ABOUT
                    )

                }
            }
        ) {
            AVANavHost(avaNavController = avaNavController, onSessionClick = onSessionClick)
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    avaNavController: NavHostController,
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    currentRoute: String?,
    destinationRoute: AVANavigationRoute
) {
    BottomNavigationItem(
        icon = { Icon(painter = painterResource(iconResId), contentDescription = stringResource(labelResId)) },
        label = { Text(stringResource(labelResId)) },
        selected = currentRoute == destinationRoute.name,
        onClick = {
            avaNavController.navigate(destinationRoute.name) {
                // Prevents from having the history of selected tabs in the backstack: back always goes to the start destination
                popUpTo(avaNavController.graph.startDestinationRoute!!) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Composable
private fun AVANavHost(
    avaNavController: NavHostController,
    onSessionClick: (sessionId: String) -> Unit
) {
    NavHost(avaNavController, startDestination = AVANavigationRoute.AGENDA.name) {
        composable(route = AVANavigationRoute.AGENDA.name) {
            AgendaLayout(onSessionClick = onSessionClick)
        }
        composable(route = AVANavigationRoute.VENUE.name) {
            VenueLayout()
        }
        composable(route = AVANavigationRoute.ABOUT.name) {
            AboutLayout()
        }
    }
}


@Preview
@Composable
private fun AVALayoutPreview() {
    AVALayout(onSessionClick = {})
}

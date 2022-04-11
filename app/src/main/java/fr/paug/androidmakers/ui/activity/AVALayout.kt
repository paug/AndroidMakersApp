package fr.paug.androidmakers.ui.activity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.androidmakers.store.model.Logo
import fr.androidmakers.store.model.Partner
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.navigation.AVANavigationRoute
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import kotlinx.coroutines.launch

/**
 * AVA stands for Agenda/Venue/About.
 *
 * This layout contains the bottom bar, and the selected layout.
 */
@Composable
fun AVALayout(
    onSessionClick: (sessionId: String) -> Unit,
) {
    val avaNavController = rememberNavController()
    val navBackStackEntry by avaNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val agendaFilterDrawerState = rememberDrawerState(DrawerValue.Closed)

    AndroidMakersTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        if (currentRoute == AVANavigationRoute.AGENDA.name) {
                            val scope = rememberCoroutineScope()
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (agendaFilterDrawerState.isClosed) agendaFilterDrawerState.open() else agendaFilterDrawerState.close()
                                    }
                                }
                            ) {
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
            },
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                AVANavHost(
                    avaNavController = avaNavController,
                    onSessionClick = onSessionClick,
                    agendaFilterDrawerState = agendaFilterDrawerState
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    avaNavController: NavHostController,
    @DrawableRes iconResId: Int,
    @StringRes labelResId: Int,
    currentRoute: String?,
    destinationRoute: AVANavigationRoute,
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
    onSessionClick: (sessionId: String) -> Unit,
    agendaFilterDrawerState: DrawerState,
) {
    NavHost(avaNavController, startDestination = AVANavigationRoute.AGENDA.name) {
        composable(route = AVANavigationRoute.AGENDA.name) {
            AgendaLayout(agendaFilterDrawerState = agendaFilterDrawerState, onSessionClick = onSessionClick)
        }
        composable(route = AVANavigationRoute.VENUE.name) {
            VenueLayout()
        }
        composable(route = AVANavigationRoute.ABOUT.name) {
            AboutLayout(
                wifiNetwork = "TODO",
                wifiPassword = "TODO",

                // TODO
                partnerList = listOf(
                    Partner(
                        order = 0,
                        title = "Event Organizers",
                        logos = listOf(
                            Logo(
                                logoUrl = "../images/logos/babbel.jpeg",
                                name = "Babbel",
                                url = "https://babbel.com/"
                            ),
                            Logo(
                                logoUrl = "../images/logos/coyote.png",
                                name = "Coyote",
                                url = "https://corporate.moncoyote.com/"
                            ),
                        )
                    ),
                    Partner(
                        order = 1,
                        title = "Gold sponsors",
                        logos = listOf(
                            Logo(
                                logoUrl = "../images/logos/deezer.png",
                                name = "Deezer",
                                url = "https://www.deezer.com/en/company/about"
                            ),
                        )
                    ),
                ),

                onFaqClick = {
                    // TODO
                },
                onCodeOfConductClick = {
                    // TODO
                },
                onTwitterHashtagClick = {
                    // TODO
                },
                onTwitterLogoClick = {
                    // TODO
                },
                onYouTubeLogoClick = {
                    // TODO
                },
                onSponsorClick = {
                    // TODO
                    println(it)
                }
            )
        }
    }
}


@Preview
@Composable
private fun AVALayoutPreview() {
    AVALayout(onSessionClick = {})
}

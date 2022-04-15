package fr.paug.androidmakers.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    aboutActions: AboutActions,
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
                    },
                    elevation = if (currentRoute == AVANavigationRoute.ABOUT.name) AppBarDefaults.TopAppBarElevation else 0.dp
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
                    agendaFilterDrawerState = agendaFilterDrawerState,
                    aboutActions = aboutActions,
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
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    agendaFilterDrawerState: DrawerState,
    aboutActions: AboutActions,
) {
    NavHost(avaNavController, startDestination = AVANavigationRoute.AGENDA.name) {
        composable(route = AVANavigationRoute.AGENDA.name) {
            AgendaLayout(agendaFilterDrawerState = agendaFilterDrawerState, onSessionClick = onSessionClick)
        }
        composable(route = AVANavigationRoute.VENUE.name) {
            VenuePager()
        }
        composable(route = AVANavigationRoute.ABOUT.name) {
            val aboutViewModel: AboutViewModel = viewModel()
            val partnerList: PartnerListState by aboutViewModel.partnerList.collectAsState(PartnerListState.Loading)
            AboutLayout(
                wifiInfo = aboutViewModel.getWifiInfo(),
                partnerList = partnerList,
                aboutActions = aboutActions
            )
        }
    }
}


@Preview
@Composable
private fun AVALayoutPreview() {
    AVALayout(onSessionClick = { _, _, _, _ -> }, aboutActions = AboutActions())
}

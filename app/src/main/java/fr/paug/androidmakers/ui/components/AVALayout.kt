package fr.paug.androidmakers.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.androidmakers.store.model.Partner
import fr.androidmakers.store.model.SpeakerId
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.AMUser
import fr.paug.androidmakers.ui.components.about.AboutActions
import fr.paug.androidmakers.ui.components.about.AboutLayout
import fr.paug.androidmakers.ui.components.agenda.AgendaLayout
import fr.paug.androidmakers.ui.components.speakers.SpeakerScreen
import fr.paug.androidmakers.ui.components.speakers.SpeakerViewModel
import fr.paug.androidmakers.ui.components.sponsors.SponsorsScreen
import fr.paug.androidmakers.ui.navigation.AVANavigationRoute
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * AVA stands for Agenda/Venue/About.
 *
 * This layout contains the bottom bar, and the selected layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AVALayout(
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    aboutActions: AboutActions,
    user: AMUser?,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {
  val avaNavController = rememberNavController()
  val navBackStackEntry by avaNavController.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route

  val agendaFilterDrawerState = rememberDrawerState(DrawerValue.Closed)
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
//                navigationIconContentColor =,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            navigationIcon = {
              Box(modifier = Modifier.padding(14.dp)) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "logo"
                )
              }
            },
            title = {
              Row(horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)) {

                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
              }
            },
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
                  Icon(
                      imageVector = Icons.Rounded.FilterList,
                      contentDescription = stringResource(R.string.filter),
                  )
                }
              }
              SigninButton(user)
            }
        )
      },

      bottomBar = {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ) {
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.CalendarMonth,
              labelResId = R.string.title_agenda,
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.AGENDA
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.LocationCity,
              labelResId = R.string.title_venue,
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.VENUE
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Groups,
              labelResId = R.string.title_speakers,
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.SPEAKERS
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Diamond,
              labelResId = R.string.title_sponsors,
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.SPONSORS
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Info,
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
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }
  }
}

@Composable
private fun RowScope.NavigationBarItem(
    avaNavController: NavHostController,
    imageVector: ImageVector,
    @StringRes labelResId: Int,
    currentRoute: String?,
    destinationRoute: AVANavigationRoute,
) {
  this@NavigationBarItem.NavigationBarItem(
      icon = {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(labelResId)
        )
      },
      label = { Text(stringResource(labelResId)) },
      selected = currentRoute == destinationRoute.name,
      colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.onSurface,
          selectedTextColor = MaterialTheme.colorScheme.onSurface,
          unselectedIconColor = MaterialTheme.colorScheme.onSurface,
          unselectedTextColor = MaterialTheme.colorScheme.onSurface,
          indicatorColor = MaterialTheme.colorScheme.surface,
//          disabledIconColor =,
//          disabledTextColor =
      ),
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
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {
  NavHost(avaNavController, startDestination = AVANavigationRoute.AGENDA.name) {

    composable(route = AVANavigationRoute.AGENDA.name) {
      AgendaLayout(
          agendaFilterDrawerState = agendaFilterDrawerState,
          onSessionClick = onSessionClick
      )
    }

    composable(route = AVANavigationRoute.VENUE.name) {
      VenuePager()
    }

    composable(route = AVANavigationRoute.SPEAKERS.name) {

      val speakerViewModel: SpeakerViewModel = viewModel(
          factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
              @Suppress("UNCHECKED_CAST")
              return SpeakerViewModel(
              ) as T
            }
          }
      )

      SpeakerScreen(
          speakerViewModel = speakerViewModel,
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }

    composable(route = AVANavigationRoute.ABOUT.name) {
      val partnerList by viewModel<PartnersViewModel>().values.collectAsState()

      AboutLayout(
          partnerList = partnerList,
          aboutActions = aboutActions
      )
    }

    composable(route = AVANavigationRoute.SPONSORS.name) {
      val partnerList by viewModel<PartnersViewModel>().values.collectAsState()
      SponsorsScreen(
          partnerList = partnerList,
          onSponsorClick = aboutActions.onSponsorClick
      )
    }

  }
}

class PartnersViewModel : LceViewModel<List<Partner>>() {
  override fun produce(): Flow<Result<List<Partner>>> {
    return AndroidMakersApplication.instance().store.getPartners()
  }
}

@Preview
@Composable
private fun AVALayoutPreview() {
  AVALayout(
      onSessionClick = { _, _, _, _ -> },
      aboutActions = AboutActions(),
      user = null,
      navigateToSpeakerDetails = {}
  )
}

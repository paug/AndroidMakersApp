package com.androidmakers.ui.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.about.AboutScreen
import com.androidmakers.ui.agenda.AgendaLayout
import com.androidmakers.ui.common.SigninButton
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.speakers.SpeakerListViewModel
import com.androidmakers.ui.speakers.SpeakerScreen
import com.androidmakers.ui.sponsors.SponsorsScreen
import com.androidmakers.ui.venue.VenuePager
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import fr.paug.androidmakers.ui.MR
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

/**
 * AVA stands for Agenda/Venue/About.
 *
 * This layout contains the bottom bar, and the selected layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AVALayout(
    versionCode: String,
    versionName: String,
    onSessionClick: (sessionId: String) -> Unit,
    navigateToSpeakerDetails: (String) -> Unit,
    signinCallbacks: SigninCallbacks,
) {
  val avaNavController = rememberNavigator()
  val navBackStackEntry by avaNavController.currentEntry.collectAsState(null)
  val currentRoute = navBackStackEntry?.route?.route
  val userFlow = remember { UserData().userRepository.user }

  val agendaFilterDrawerState = rememberDrawerState(DrawerValue.Closed)
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  val user by userFlow.collectAsStateWithLifecycle()

  Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            navigationIcon = {
              Box(modifier = Modifier.padding(14.dp)) {
                Image(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(MR.images.notification),
                    contentDescription = "logo"
                )
              }
            },
            title = {
              Row(horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)) {

                Text(
                    text = stringResource(MR.strings.app_name),
                    style = MaterialTheme.typography.titleSmall,
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
                      contentDescription = stringResource(MR.strings.filter),
                  )
                }
              }
              SigninButton(user, signinCallbacks)
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
              label = stringResource(MR.strings.agenda),
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.AGENDA
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.LocationCity,
              label = stringResource(MR.strings.venue),
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.VENUE
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Groups,
              label = stringResource(MR.strings.speakers),
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.SPEAKERS
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Diamond,
              label = stringResource(MR.strings.sponsors),
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.SPONSORS
          )
          NavigationBarItem(
              avaNavController = avaNavController,
              imageVector = Icons.Rounded.Info,
              label = stringResource(MR.strings.about),
              currentRoute = currentRoute,
              destinationRoute = AVANavigationRoute.ABOUT
          )
        }
      },
  ) { innerPadding ->
    Box(Modifier.padding(innerPadding)) {
      AVANavHost(
          versionCode = versionCode,
          versionName = versionName,
          avaNavController = avaNavController,
          onSessionClick = onSessionClick,
          agendaFilterDrawerState = agendaFilterDrawerState,
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }
  }
}

@Composable
private fun RowScope.NavigationBarItem(
    avaNavController: Navigator,
    imageVector: ImageVector,
    label: String,
    currentRoute: String?,
    destinationRoute: AVANavigationRoute,
) {
  this@NavigationBarItem.NavigationBarItem(
      icon = {
        Icon(
            imageVector = imageVector,
            contentDescription = label
        )
      },
      label = { Text(label) },
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
        avaNavController.navigate(destinationRoute.name, options = NavOptions(
            launchSingleTop = true,
            popUpTo = PopUpTo.First(inclusive = true)
        )
        )
      }
  )
}

@Composable
private fun AVANavHost(
    versionCode: String,
    versionName: String,
    avaNavController: Navigator,
    onSessionClick: (sessionId: String) -> Unit,
    agendaFilterDrawerState: DrawerState,
    navigateToSpeakerDetails: (String) -> Unit,
) {
  NavHost(avaNavController, initialRoute = AVANavigationRoute.AGENDA.name) {

    scene(
      route = AVANavigationRoute.AGENDA.name,
      navTransition = defaultNavTransition
    ) {
      AgendaLayout(
          agendaFilterDrawerState = agendaFilterDrawerState,
          onSessionClick = onSessionClick
      )
    }

    scene(
      route = AVANavigationRoute.VENUE.name,
      navTransition = defaultNavTransition
    ) {
      VenuePager()
    }

    scene(
      route = AVANavigationRoute.SPEAKERS.name,
      navTransition = defaultNavTransition
    ) {

      val speakerViewModel = koinViewModel(vmClass = SpeakerListViewModel::class)
      SpeakerScreen(
          viewModel = speakerViewModel,
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }

    scene(
      route = AVANavigationRoute.ABOUT.name,
      navTransition = defaultNavTransition
    ) {
      AboutScreen(
          versionCode = versionCode,
          versionName = versionName
      )
    }

    scene(
      route = AVANavigationRoute.SPONSORS.name,
      navTransition = defaultNavTransition
    ) {
      SponsorsScreen()
    }
  }
}

private val defaultNavTransition = NavTransition(
  createTransition = fadeIn(),
  pauseTransition = fadeOut(),
  resumeTransition = fadeIn(),
  destroyTransition = fadeOut()
)

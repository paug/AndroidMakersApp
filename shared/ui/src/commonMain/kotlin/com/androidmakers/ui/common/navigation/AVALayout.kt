package com.androidmakers.ui.common.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.LocalPadding
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
import fr.androidmakers.domain.model.Speaker
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AVALayout(
  versionCode: String,
  versionName: String,
  onSessionClick: (sessionId: String) -> Unit,
  navigateToSpeakerDetails: (Speaker) -> Unit,
  signinCallbacks: SigninCallbacks,
) {
  val sizeClass = calculateWindowSizeClass()

  val avaNavController = rememberNavigator()
  val navBackStackEntry by avaNavController.currentEntry.collectAsState(null)
  val currentRoute = navBackStackEntry?.route?.route
  val userFlow = remember { UserData().userRepository.user }

  val agendaFilterDrawerState = rememberDrawerState(DrawerValue.Closed)
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

  val user by userFlow.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      if (sizeClass.heightSizeClass != WindowHeightSizeClass.Compact) {
        MediumTopAppBar(
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
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          },
          actions = {
            if (currentRoute == AVANavigationRoute.AGENDA.name && sizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
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
      }
    },

    bottomBar = {
      if (sizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        NavBar(
          avaNavController,
          currentRoute
        )
      }
    },
  ) { innerPadding ->

    CompositionLocalProvider(LocalPadding provides innerPadding) {
      Row(Modifier.fillMaxSize()) {
        if (sizeClass.widthSizeClass > WindowWidthSizeClass.Compact) {
          RailBar(
            avaNavController,
            currentRoute
          )
        }

        Box(Modifier.padding(
          top = innerPadding.calculateTopPadding(),
          start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
          end = innerPadding.calculateEndPadding(LayoutDirection.Ltr)
        )) {

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
  }
}

@Composable
private fun NavBar(
  navController: Navigator,
  currentRoute: String?
) {
  NavigationBar(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.onBackground
  ) {
    NavigationBarItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.CalendarMonth,
      label = stringResource(MR.strings.agenda),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.AGENDA
    )
    NavigationBarItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.LocationCity,
      label = stringResource(MR.strings.venue),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.VENUE
    )
    NavigationBarItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Groups,
      label = stringResource(MR.strings.speakers),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.SPEAKERS
    )
    NavigationBarItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Diamond,
      label = stringResource(MR.strings.sponsors),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.SPONSORS
    )
    NavigationBarItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Info,
      label = stringResource(MR.strings.about),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.ABOUT
    )
  }
}

@Composable
private fun RailBar(
  navController: Navigator,
  currentRoute: String?
) {
  NavigationRail(

  ) {
    NavRailItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.CalendarMonth,
      label = stringResource(MR.strings.agenda),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.AGENDA
    )
    NavRailItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.LocationCity,
      label = stringResource(MR.strings.venue),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.VENUE
    )
    NavRailItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Groups,
      label = stringResource(MR.strings.speakers),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.SPEAKERS
    )
    NavRailItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Diamond,
      label = stringResource(MR.strings.sponsors),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.SPONSORS
    )
    NavRailItem(
      avaNavController = navController,
      imageVector = Icons.Rounded.Info,
      label = stringResource(MR.strings.about),
      currentRoute = currentRoute,
      destinationRoute = AVANavigationRoute.ABOUT
    )
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
private fun NavRailItem(
  avaNavController: Navigator,
  imageVector: ImageVector,
  label: String,
  currentRoute: String?,
  destinationRoute: AVANavigationRoute,
) {
  NavigationRailItem(
    icon = {
      Icon(
        imageVector = imageVector,
        contentDescription = label
      )
    },
    label = { Text(label) },
    selected = currentRoute == destinationRoute.name,
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
    navigateToSpeakerDetails: (Speaker) -> Unit,
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

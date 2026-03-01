package com.androidmakers.ui.common.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material.icons.rounded.DynamicFeed
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationCity
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.androidmakers.ui.about.AboutScreen
import com.androidmakers.ui.agenda.AgendaLayout
import com.androidmakers.ui.agenda.SessionDetailScreen
import com.androidmakers.ui.common.BackHandlerCompat
import com.androidmakers.ui.common.SigninButton
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.feed.FeedScreen
import com.androidmakers.ui.speakers.SpeakerDetailsRoute
import com.androidmakers.ui.speakers.SpeakerScreen
import com.androidmakers.ui.sponsors.SponsorsScreen
import com.androidmakers.ui.venue.VenuePager
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.utils.FeatureFlags
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.about
import fr.paug.androidmakers.ui.agenda
import fr.paug.androidmakers.ui.app_name
import fr.paug.androidmakers.ui.feed
import fr.paug.androidmakers.ui.filter
import fr.paug.androidmakers.ui.notification
import fr.paug.androidmakers.ui.speakers
import fr.paug.androidmakers.ui.sponsors
import fr.paug.androidmakers.ui.venue
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * AVA stands for Agenda/Venue/About.
 *
 * This layout contains the top bar, bottom bar, and the navigation display.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AVALayout(
    versionCode: String,
    versionName: String,
    navigationState: NavigationState,
    navigator: Navigator,
    signinCallbacks: SigninCallbacks,
    userRepository: UserRepository = koinInject(),
) {
  var showAgendaFilterBottomSheet by remember { mutableStateOf(false) }
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  LaunchedEffect(navigationState.topLevelRoute) {
    showAgendaFilterBottomSheet = false
  }

  val user by userRepository.user.collectAsStateWithLifecycle(userRepository.currentUser)

  // Check if the current top entry is a detail screen (to hide bottom bar)
  val currentStack = navigationState.backStacks[navigationState.topLevelRoute]
  val currentTopEntry = currentStack?.lastOrNull()
  val showBottomBar = currentTopEntry == null || currentTopEntry.isTabKey()

  val entryProvider = entryProvider {
    // Tab entries
    entry<FeedKey> {
      FeedScreen()
    }

    entry<AgendaKey> {
      AgendaLayout(
        showFilterBottomSheet = showAgendaFilterBottomSheet,
        onFilterBottomSheetDismiss = { showAgendaFilterBottomSheet = false },
        onSessionClick = { sessionId -> navigator.navigate(SessionDetailKey(sessionId)) }
      )
    }

    entry<VenueKey> {
      VenuePager()
    }

    entry<SpeakersKey> {
      SpeakerScreen(
        viewModel = koinViewModel(),
        navigateToSpeakerDetails = { speakerId -> navigator.navigate(SpeakerDetailKey(speakerId)) }
      )
    }

    entry<SponsorsKey> {
      SponsorsScreen()
    }

    entry<AboutKey> {
      AboutScreen(
        versionCode = versionCode,
        versionName = versionName
      )
    }

    // Detail entries
    entry<SessionDetailKey> { key ->
      SessionDetailScreen(
        viewModel = koinViewModel { parametersOf(key.sessionId) },
        onBackClick = { navigator.goBack() },
        onSpeakerClick = { speakerId -> navigator.navigate(SpeakerDetailKey(speakerId)) },
      )
    }

    entry<SpeakerDetailKey> { key ->
      SpeakerDetailsRoute(
        speakerDetailsViewModel = koinViewModel { parametersOf(key.speakerId) },
        onBackClick = { navigator.goBack() },
      )
    }
  }

  Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        if (showBottomBar) {
          TopAppBar(
              scrollBehavior = scrollBehavior,
              colors = TopAppBarDefaults.topAppBarColors(
                  scrolledContainerColor = MaterialTheme.colorScheme.surface,
                  actionIconContentColor = MaterialTheme.colorScheme.onSurface,
              ),
              navigationIcon = {
                Box(modifier = Modifier.padding(12.dp)) {
                  Image(
                      modifier = Modifier.size(32.dp),
                      painter = painterResource(Res.drawable.notification),
                      contentDescription = "logo"
                  )
                }
              },
              title = {
                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
              },
              actions = {
                if (navigationState.topLevelRoute is AgendaKey) {
                  BackHandlerCompat(enabled = showAgendaFilterBottomSheet) {
                    showAgendaFilterBottomSheet = false
                  }
                  IconButton(
                      onClick = { showAgendaFilterBottomSheet = !showAgendaFilterBottomSheet }
                  ) {
                    Icon(
                        imageVector = Icons.Rounded.FilterList,
                        contentDescription = stringResource(Res.string.filter),
                    )
                  }
                }
                SigninButton(user, signinCallbacks)
              }
          )
        }
      },

      bottomBar = {
        if (showBottomBar) {
          NavigationBar {
            if (FeatureFlags.isFeedEnabled) {
              NavigationBarItem(
                  navigator = navigator,
                  navigationState = navigationState,
                  imageVector = Icons.Rounded.DynamicFeed,
                  label = stringResource(Res.string.feed),
                  destinationRoute = FeedKey
              )
            }
            NavigationBarItem(
                navigator = navigator,
                navigationState = navigationState,
                imageVector = Icons.Rounded.CalendarMonth,
                label = stringResource(Res.string.agenda),
                destinationRoute = AgendaKey
            )
            NavigationBarItem(
                navigator = navigator,
                navigationState = navigationState,
                imageVector = Icons.Rounded.LocationCity,
                label = stringResource(Res.string.venue),
                destinationRoute = VenueKey
            )
            NavigationBarItem(
                navigator = navigator,
                navigationState = navigationState,
                imageVector = Icons.Rounded.Groups,
                label = stringResource(Res.string.speakers),
                destinationRoute = SpeakersKey
            )
            NavigationBarItem(
                navigator = navigator,
                navigationState = navigationState,
                imageVector = Icons.Rounded.Diamond,
                label = stringResource(Res.string.sponsors),
                destinationRoute = SponsorsKey
            )
            NavigationBarItem(
                navigator = navigator,
                navigationState = navigationState,
                imageVector = Icons.Rounded.Info,
                label = stringResource(Res.string.about),
                destinationRoute = AboutKey
            )
          }
        }
      },
  ) { innerPadding ->
    Box(
      Modifier.padding(innerPadding)
        .consumeWindowInsets(innerPadding)
    ) {
      NavDisplay(
        entries = navigationState.toDecoratedEntries(entryProvider),
        onBack = { navigator.goBack() }
      )
    }
  }
}

@Composable
private fun RowScope.NavigationBarItem(
    navigator: Navigator,
    navigationState: NavigationState,
    imageVector: ImageVector,
    label: String,
    destinationRoute: NavKey,
) {
  NavigationBarItem(
      icon = {
        Icon(
            imageVector = imageVector,
            contentDescription = label
        )
      },
      label = { Text(label) },
      selected = navigationState.topLevelRoute == destinationRoute,
      colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          unselectedIconColor = MaterialTheme.colorScheme.outline,
          unselectedTextColor = MaterialTheme.colorScheme.outline,
          indicatorColor = MaterialTheme.colorScheme.primaryContainer,
      ),
      onClick = {
        navigator.navigate(destinationRoute)
      }
  )
}

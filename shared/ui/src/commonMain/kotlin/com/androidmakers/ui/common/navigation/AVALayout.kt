package com.androidmakers.ui.common.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.DynamicFeed
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
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
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.androidmakers.ui.agenda.AgendaLayout
import com.androidmakers.ui.agenda.SessionDetailScreen
import com.androidmakers.ui.common.BackHandlerCompat
import com.androidmakers.ui.common.SigninButton
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.feed.FeedScreen
import com.androidmakers.ui.info.InfoScreen
import com.androidmakers.ui.speakers.SpeakerDetailsRoute
import com.androidmakers.ui.speakers.SpeakerScreen
import com.androidmakers.ui.sponsors.SponsorsScreen
import fr.androidmakers.domain.model.FeatureFlags
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.agenda
import fr.paug.androidmakers.ui.feed
import fr.paug.androidmakers.ui.filter
import fr.paug.androidmakers.ui.info
import fr.paug.androidmakers.ui.notification
import fr.paug.androidmakers.ui.speakers
import fr.paug.androidmakers.ui.sponsors
import fr.paug.androidmakers.ui.top_bar_subtitle
import fr.paug.androidmakers.ui.top_bar_title
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AVALayout(
  versionCode: String,
  versionName: String,
  navigationState: NavigationState,
  navigator: Navigator,
  signinCallbacks: SigninCallbacks,
  userRepository: UserRepository = koinInject(),
  featureFlags: FeatureFlags
) {
  var showAgendaFilterBottomSheet by remember { mutableStateOf(false) }
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  LaunchedEffect(navigationState.topLevelRoute) {
    showAgendaFilterBottomSheet = false
  }

  val user by userRepository.user.collectAsStateWithLifecycle(userRepository.currentUser)

  // On wide screens with list-detail, both panes are shown together,
  // so we keep bars visible even when a detail entry is on top.
  val windowAdaptiveInfo = currentWindowAdaptiveInfo()
  val isWideScreen = windowAdaptiveInfo.windowSizeClass
    .isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)
  val currentStack = navigationState.backStacks[navigationState.topLevelRoute]
  val currentTopEntry = currentStack?.lastOrNull()
  val showBottomBar = currentTopEntry == null || currentTopEntry.isTabKey()
    || (isWideScreen && currentTopEntry is SessionDetailKey)

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      if (showBottomBar) {
        AVATopAppBar(
          scrollBehavior = scrollBehavior,
          isAgendaRoute = navigationState.topLevelRoute is AgendaKey,
          showAgendaFilterBottomSheet = showAgendaFilterBottomSheet,
          onToggleFilter = { showAgendaFilterBottomSheet = !showAgendaFilterBottomSheet },
          onDismissFilter = { showAgendaFilterBottomSheet = false },
          user = user,
          signinCallbacks = signinCallbacks,
        )
      }
    },
    bottomBar = {
      if (showBottomBar) {
        AVABottomBar(
          navigator = navigator,
          navigationState = navigationState,
          featureFlags = featureFlags
        )
      }
    },
  ) { innerPadding ->
    Box(
      Modifier.padding(innerPadding)
        .consumeWindowInsets(innerPadding)
    ) {
      AVANavDisplay(
        versionCode = versionCode,
        versionName = versionName,
        navigationState = navigationState,
        navigator = navigator,
        showAgendaFilterBottomSheet = showAgendaFilterBottomSheet,
        onDismissAgendaFilter = { showAgendaFilterBottomSheet = false },
        isWideScreen = isWideScreen,
        featureFlags = featureFlags,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AVATopAppBar(
  scrollBehavior: TopAppBarScrollBehavior,
  isAgendaRoute: Boolean,
  showAgendaFilterBottomSheet: Boolean,
  onToggleFilter: () -> Unit,
  onDismissFilter: () -> Unit,
  user: User?,
  signinCallbacks: SigninCallbacks,
) {
  TopAppBar(
    scrollBehavior = scrollBehavior,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
      scrolledContainerColor = MaterialTheme.colorScheme.background,
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
      Column {
        Text(
          text = stringResource(Res.string.top_bar_title),
          style = MaterialTheme.typography.titleMedium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = stringResource(Res.string.top_bar_subtitle),
          style = MaterialTheme.typography.titleSmall,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    },
    actions = {
      if (isAgendaRoute) {
        BackHandlerCompat(enabled = showAgendaFilterBottomSheet) {
          onDismissFilter()
        }
        IconButton(
          onClick = onToggleFilter
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

@Composable
private fun AVABottomBar(
  navigator: Navigator,
  navigationState: NavigationState,
  featureFlags: FeatureFlags
) {
  Column {
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {

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
        imageVector = Icons.Rounded.Groups,
        label = stringResource(Res.string.speakers),
        destinationRoute = SpeakersKey
      )

      if (featureFlags.feed) {
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
        imageVector = Icons.Rounded.Favorite,
        label = stringResource(Res.string.sponsors),
        destinationRoute = SponsorsKey
      )

      NavigationBarItem(
        navigator = navigator,
        navigationState = navigationState,
        imageVector = Icons.Rounded.Info,
        label = stringResource(Res.string.info),
        destinationRoute = InfoKey
      )

    }
  }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun AVANavDisplay(
  versionCode: String,
  versionName: String,
  navigationState: NavigationState,
  navigator: Navigator,
  showAgendaFilterBottomSheet: Boolean,
  onDismissAgendaFilter: () -> Unit,
  isWideScreen: Boolean,
  featureFlags: FeatureFlags,
) {
  SharedTransitionLayout {
    val sharedTransitionScope = this

    val entryProvider = entryProvider {
      // Tab entries
      entry<FeedKey> {
        FeedScreen()
      }

      entry<AgendaKey>(
        metadata = ListDetailSceneStrategy.listPane(
          detailPlaceholder = {}
        )
      ) {
        AgendaLayout(
          showFilterBottomSheet = showAgendaFilterBottomSheet,
          onFilterBottomSheetDismiss = onDismissAgendaFilter,
          onSessionClick = { sessionId -> navigator.navigateToSessionDetail(sessionId) }
        )
      }

      entry<SpeakersKey> {
        SpeakerScreen(
          viewModel = koinViewModel(),
          navigateToSpeakerDetails = { speakerId -> navigator.navigate(SpeakerDetailKey(speakerId)) },
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = LocalNavAnimatedContentScope.current,
        )
      }

      entry<SponsorsKey> {
        SponsorsScreen()
      }

      entry<InfoKey> {
        InfoScreen(
          versionCode = versionCode,
          versionName = versionName,
          featureFlags = featureFlags,
        )
      }

      // Detail entries
      entry<SessionDetailKey>(
        metadata = ListDetailSceneStrategy.detailPane()
      ) { key ->
        SessionDetailScreen(
          viewModel = koinViewModel(key = key.sessionId) { parametersOf(key.sessionId) },
          onBackClick = { navigator.goBack() },
          onSpeakerClick = { speakerId -> navigator.navigate(SpeakerDetailKey(speakerId)) },
          showBackButton = !isWideScreen,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = LocalNavAnimatedContentScope.current,
        )
      }

      entry<SpeakerDetailKey> { key ->
        SpeakerDetailsRoute(
          speakerDetailsViewModel = koinViewModel(key = key.speakerId) { parametersOf(key.speakerId) },
          onBackClick = { navigator.goBack() },
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = LocalNavAnimatedContentScope.current,
        )
      }
    }

    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    NavDisplay(
      entries = navigationState.toDecoratedEntries(entryProvider),
      sceneStrategies = listOf(listDetailStrategy),
      onBack = { navigator.goBack() }
    )
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
    label = { Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
    selected = navigationState.topLevelRoute == destinationRoute,
    colors = NavigationBarItemDefaults.colors(
      selectedIconColor = MaterialTheme.colorScheme.primary,
      selectedTextColor = MaterialTheme.colorScheme.primary,
      unselectedIconColor = MaterialTheme.colorScheme.onSurface,
      unselectedTextColor = MaterialTheme.colorScheme.onSurface,
      indicatorColor = MaterialTheme.colorScheme.primaryContainer,
    ),
    onClick = {
      navigator.navigate(destinationRoute)
    }
  )
}

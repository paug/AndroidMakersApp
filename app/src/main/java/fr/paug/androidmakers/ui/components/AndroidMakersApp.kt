package fr.paug.androidmakers.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import fr.paug.androidmakers.ui.AMUser
import fr.paug.androidmakers.ui.navigation.AndroidMakersBottomNavigationBar
import fr.paug.androidmakers.ui.navigation.AndroidMakersNavigationActions
import fr.paug.androidmakers.ui.navigation.AndroidMakersNavigationRail
import fr.paug.androidmakers.ui.navigation.AndroidMakersRoute
import fr.paug.androidmakers.ui.navigation.AndroidMakersTopAppBar
import fr.paug.androidmakers.ui.navigation.AndroidMakersTopLevelDestination
import fr.paug.androidmakers.ui.navigation.aboutGraph
import fr.paug.androidmakers.ui.navigation.agendaGraph
import fr.paug.androidmakers.ui.navigation.speakersGraph
import fr.paug.androidmakers.ui.navigation.sponsorsGraph
import fr.paug.androidmakers.ui.navigation.venueGraph
import fr.paug.androidmakers.ui.util.extension.findActivity
import fr.paug.androidmakers.util.AndroidMakersContentType
import fr.paug.androidmakers.util.AndroidMakersNavigationContentPosition
import fr.paug.androidmakers.util.AndroidMakersNavigationType
import fr.paug.androidmakers.util.DevicePosture
import fr.paug.androidmakers.util.isBookPosture
import fr.paug.androidmakers.util.isSeparating

@Composable
fun AndroidMakersApp(
    displayFeatures: List<DisplayFeature>,
    windowSizeClass: WindowSizeClass,
    user: AMUser?
) {
  /**
   * This will help us select type of navigation and content type depending on window size and
   * fold state of the device.
   */
  val navigationType: AndroidMakersNavigationType
  val contentType: AndroidMakersContentType

  /**
   * We are using display's folding features to map the device postures a fold is in.
   * In the state of folding device If it's half fold in BookPosture we want to avoid content
   * at the crease/hinge
   */
  val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

  val foldingDevicePosture = when {
    isBookPosture(foldingFeature) -> DevicePosture.BookPosture(foldingFeature.bounds)
    isSeparating(foldingFeature) -> DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
    else -> DevicePosture.NormalPosture
  }

  /**
   * This will help us select type of navigation and content type depending on window size and
   * fold state of the device.
   */
  when (windowSizeClass.widthSizeClass) {
    WindowWidthSizeClass.Compact -> {
      navigationType = AndroidMakersNavigationType.BOTTOM_NAVIGATION
      contentType = AndroidMakersContentType.SINGLE_PANE
    }
    WindowWidthSizeClass.Medium -> {
      navigationType = AndroidMakersNavigationType.NAVIGATION_RAIL
      contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
        AndroidMakersContentType.DUAL_PANE
      } else {
        AndroidMakersContentType.SINGLE_PANE
      }
    }
    WindowWidthSizeClass.Expanded -> {
      navigationType = AndroidMakersNavigationType.NAVIGATION_RAIL
      contentType = AndroidMakersContentType.DUAL_PANE
    }
    else -> {
      navigationType = AndroidMakersNavigationType.BOTTOM_NAVIGATION
      contentType = AndroidMakersContentType.SINGLE_PANE
    }
  }

  /**
   * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
   * ergonomics and reachability depending upon the height of the device.
   */
  val navigationContentPosition = when (windowSizeClass.heightSizeClass) {
    WindowHeightSizeClass.Compact -> {
      AndroidMakersNavigationContentPosition.TOP
    }
    WindowHeightSizeClass.Medium,
    WindowHeightSizeClass.Expanded -> {
      AndroidMakersNavigationContentPosition.CENTER
    }
    else -> {
      AndroidMakersNavigationContentPosition.TOP
    }
  }

  val navHostController = rememberNavController()
  val navigationActions = remember(navHostController) {
    AndroidMakersNavigationActions(navHostController)
  }

  val navBackStackEntry by navHostController.currentBackStackEntryAsState()
  val selectedDestination = navBackStackEntry?.destination

  AndroidMakersAppContent(
      contentType = contentType,
      displayFeatures = displayFeatures,
      navigationContentPosition = navigationContentPosition,
      navHostController = navHostController,
      navigationType = navigationType,
      selectedDestination = selectedDestination,
      user = user,
      navigateToTopLevelDestination = navigationActions::navigateTo
  )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AndroidMakersAppContent(
    modifier: Modifier = Modifier,
    contentType: AndroidMakersContentType,
    displayFeatures: List<DisplayFeature>,
    navHostController: NavHostController,
    navigationContentPosition: AndroidMakersNavigationContentPosition,
    navigationType: AndroidMakersNavigationType,
    selectedDestination: NavDestination?,
    user: AMUser?,
    navigateToTopLevelDestination: (AndroidMakersTopLevelDestination) -> Unit
) {
  Row(
      modifier = modifier
          .fillMaxSize()
          .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))
  ) {
    AnimatedVisibility(visible = navigationType == AndroidMakersNavigationType.NAVIGATION_RAIL) {
      AndroidMakersNavigationRail(
          navigateToTopLevelDestination = navigateToTopLevelDestination,
          navigationContentPosition = navigationContentPosition,
          selectedDestination = selectedDestination
      )
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
          AndroidMakersTopAppBar(
              navHostController = navHostController,
              user = user
          )
        },
        bottomBar = {
          AnimatedVisibility(visible = navigationType == AndroidMakersNavigationType.BOTTOM_NAVIGATION) {
            AndroidMakersBottomNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination
            )
          }
        }
    ) { paddingValues ->
      AndroidMakersRootNavHost(
          modifier = Modifier
              .padding(paddingValues)
              .consumeWindowInsets(paddingValues)
              .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
          contentType = contentType,
          displayFeatures = displayFeatures,
          navHostController = navHostController,
          navigationType = navigationType
      )
    }
  }
}

/**
 * Root navigation graph
 */
@Composable
private fun AndroidMakersRootNavHost(
    modifier: Modifier = Modifier,
    contentType: AndroidMakersContentType,
    displayFeatures: List<DisplayFeature>,
    navHostController: NavHostController,
    navigationType: AndroidMakersNavigationType
) {
  NavHost(
      modifier = modifier,
      navController = navHostController,
      route = AndroidMakersRoute.ROOT_ROUTE,
      startDestination = AndroidMakersRoute.AGENDA_ROUTE,
  ) {
    agendaGraph(navHostController = navHostController)
    venueGraph()
    speakersGraph(navHostController = navHostController)
    sponsorsGraph()
    aboutGraph()
  }
}
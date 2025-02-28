package com.androidmakers.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.androidmakers.ui.agenda.SessionDetailScreen
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.common.navigation.AVALayout
import com.androidmakers.ui.common.navigation.MainNavigationRoute
import com.androidmakers.ui.speakers.SpeakerDetailsRoute
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.SpeakerId
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * The main layout: entry point of the application
 */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainLayout(
    versionCode: String,
    versionName: String,
    signinCallbacks: SigninCallbacks,
    deeplink: String? = null,
) {
  setSingletonImageLoaderFactory { context ->
    ImageLoader.Builder(context)
      .crossfade(true)
      .build()
  }

  val navController = rememberNavController()
  MainNavHost(
      mainNavController = navController,
      onSessionClick = { sessionId ->
        navController.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId")
      },
      navigateToSpeakerDetails = { speakerId ->
        navController.navigate("${MainNavigationRoute.SPEAKER_DETAIL.name}/$speakerId")
      },
      versionCode = versionCode,
      versionName = versionName,
      signingCallbacks = signinCallbacks,
      deeplink = deeplink,
  )
}

@Composable
private fun MainNavHost(
    versionCode: String,
    versionName: String,
    mainNavController: NavHostController,
    onSessionClick: (sessionId: String) -> Unit,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
    signingCallbacks: SigninCallbacks,
    deeplink: String? = null,
) {
  LaunchedEffect(deeplink) {
    deeplink?.let {
      try {
        mainNavController.navigate(deeplink)
      } catch (e: IllegalStateException) {
        // Invalid route URL
      }
    }
  }

  NavHost(
    navController = mainNavController,
    startDestination = MainNavigationRoute.AVA.name,
    enterTransition = { defaultEnterTransition },
    exitTransition = { defaultExitTransition },
    popEnterTransition = { defaultPopEnterTransition },
    popExitTransition = { defaultPopExitTransition }
  ) {

    composable(route = MainNavigationRoute.AVA.name) {
      AVALayout(
        versionCode = versionCode,
        versionName = versionName,
        onSessionClick = onSessionClick,
        navigateToSpeakerDetails = navigateToSpeakerDetails,
        signinCallbacks = signingCallbacks,
      )
    }

    composable(
      route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}",
      deepLinks = listOf(
        NavDeepLink.Builder()
          .setUriPattern("https://androidmakers.fr/session/{sessionId}")
          .build()
      )
    ) { backStackEntry ->
      val sessionId = backStackEntry.arguments?.getString("sessionId").orEmpty()

      SessionDetailScreen(
        viewModel = koinViewModel { parametersOf(sessionId) },
        onBackClick = { mainNavController.popBackStack() },
      )
    }

    composable(
      route = "${MainNavigationRoute.SPEAKER_DETAIL.name}/{speakerId}",
      deepLinks = listOf(
        NavDeepLink.Builder()
          .setUriPattern("https://androidmakers.fr/speaker/{speakerId}")
          .build()
      )
    ) { backstackEntry ->
      val speakerId = backstackEntry.arguments?.getString("speakerId").orEmpty()

      SpeakerDetailsRoute(
        speakerDetailsViewModel = koinViewModel { parametersOf(speakerId) },
        onBackClick = { mainNavController.popBackStack() },
      )
    }
  }
}

expect val defaultEnterTransition: EnterTransition
expect val defaultExitTransition: ExitTransition
expect val defaultPopEnterTransition: EnterTransition
expect val defaultPopExitTransition: ExitTransition

@Composable
expect fun getPlatformContext(): PlatformContext

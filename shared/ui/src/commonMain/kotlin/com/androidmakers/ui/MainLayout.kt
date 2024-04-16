package com.androidmakers.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import com.androidmakers.ui.agenda.SessionDetailScreen
import com.androidmakers.ui.agenda.SessionDetailViewModel
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.common.navigation.AVALayout
import com.androidmakers.ui.common.navigation.MainNavigationRoute
import com.androidmakers.ui.speakers.SpeakerDetailsRoute
import com.androidmakers.ui.speakers.SpeakerDetailsViewModel
import fr.androidmakers.domain.model.SpeakerId
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.core.parameter.parametersOf

/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(
    versionCode: String,
    versionName: String,
    signinCallbacks: SigninCallbacks,
    deeplink: String? = null,
) {
  val navigator = rememberNavigator()
  MainNavHost(
      mainNavController = navigator,
      onSessionClick = { sessionId ->
        navigator.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId")
      },
      navigateToSpeakerDetails = { speakerId ->
        navigator.navigate("${MainNavigationRoute.SPEAKER_DETAIL.name}/$speakerId")
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
    mainNavController: Navigator,
    onSessionClick: (sessionId: String) -> Unit,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
    signingCallbacks: SigninCallbacks,
    deeplink: String? = null,
) {
    LaunchedEffect(deeplink) {
        deeplink?.let {
            mainNavController.navigate(deeplink)
        }
    }

  NavHost(
      navigator = mainNavController,
      initialRoute = MainNavigationRoute.AVA.name
  ) {

    scene(route = MainNavigationRoute.AVA.name) {

      AVALayout(
          versionCode = versionCode,
          versionName = versionName,
          onSessionClick = onSessionClick,
          navigateToSpeakerDetails = navigateToSpeakerDetails,
          signinCallbacks = signingCallbacks,
      )
    }

    scene(
        route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}",
        swipeProperties = SwipeProperties(),
        deepLinks = listOf("https://androidmakers.fr/session/{sessionId}"),
        navTransition = defaultTransition
    ) {

      val sessionId = it.path<String>("sessionId")
      val sessionDetailViewModel = koinViewModel(vmClass = SessionDetailViewModel::class) { parametersOf(sessionId) }

      SessionDetailScreen(
          viewModel = sessionDetailViewModel,
          onBackClick = { mainNavController.popBackStack() },
      )
    }

    scene(
        route = "${MainNavigationRoute.SPEAKER_DETAIL.name}/{speakerId}",
        deepLinks = listOf("https://androidmakers.fr/speaker/{speakerId}"),
        swipeProperties = SwipeProperties(),
        navTransition = defaultTransition
    ) { backstackEntry ->
      val speakerId = backstackEntry.path<String>("speakerId")

      val speakerDetailsViewModel = koinViewModel(vmClass = SpeakerDetailsViewModel::class) { parametersOf(speakerId) }

      SpeakerDetailsRoute(
          speakerDetailsViewModel = speakerDetailsViewModel,
          onBackClick = { mainNavController.popBackStack() },
      )
    }
  }
}

expect val defaultTransition: NavTransition

expect class PlatformContext

val LocalPlatformContext = staticCompositionLocalOf<Any?> { null }

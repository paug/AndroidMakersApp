package com.androidmakers.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.androidmakers.ui.about.AboutActions
import com.androidmakers.ui.common.navigation.AVALayout
import com.androidmakers.ui.common.navigation.MainNavigationRoute
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.speakers.SpeakerDetailsRoute
import com.androidmakers.ui.speakers.SpeakerDetailsViewModel
import fr.androidmakers.domain.model.SpeakerId
import fr.androidmakers.domain.model.User
import com.androidmakers.ui.agenda.SessionDetailViewModel
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.core.parameter.parametersOf

/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(
    versionCode: String,
    versionName: String,
    aboutActions: AboutActions,
    user: User?
) {
  val navigator = rememberNavigator()
  MainNavHost(
      mainNavController = navigator,
      onSessionClick = { sessionId, roomId, startTimestamp, endTimestamp ->
        navigator.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId")
      },
      aboutActions = aboutActions,
      user = user,
      navigateToSpeakerDetails = { speakerId ->
        navigator.navigate("${MainNavigationRoute.SPEAKER_DETAIL.name}/$speakerId")
      },
      versionCode = versionCode,
      versionName = versionName,
  )
}

@Composable
private fun MainNavHost(
    versionCode: String,
    versionName: String,
    mainNavController: Navigator,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    aboutActions: AboutActions,
    user: User?,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {
  NavHost(
      navigator = mainNavController,
      initialRoute = MainNavigationRoute.AVA.name
  ) {

    scene(route = MainNavigationRoute.AVA.name) {
      AVALayout(
          versionCode = versionCode,
          versionName = versionName,
          onSessionClick = onSessionClick,
          aboutActions = aboutActions,
          user = user,
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }

    scene(
        route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}",
    ) {

      Text("ok")
      /*val sessionId = it.path<String>("sessionId")
      val sessionDetailViewModel = koinViewModel(vmClass = SessionDetailViewModel::class) { parametersOf(sessionId) }

      val sessionDetailState by sessionDetailViewModel.sessionDetailState.collectAsState(
          initial = Lce.Loading
      )
      SessionDetailLayout(
          sessionDetailState = sessionDetailState,
          onBackClick = { mainNavController.popBackStack() },
          onBookmarkClick = { bookmarked -> sessionDetailViewModel.bookmark(bookmarked) },
      )*/
    }

    scene(
        route = "${MainNavigationRoute.SPEAKER_DETAIL.name}/{speakerId}"
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

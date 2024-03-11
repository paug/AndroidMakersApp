package fr.paug.androidmakers.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.androidmakers.domain.model.SpeakerId
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.ui.components.about.AboutActions
import fr.paug.androidmakers.ui.components.session.SessionDetailLayout
import fr.paug.androidmakers.ui.components.session.SessionDetailViewModel
import fr.paug.androidmakers.ui.components.speakers.details.SpeakerDetailsRoute
import fr.paug.androidmakers.ui.components.speakers.details.SpeakerDetailsViewModel
import fr.paug.androidmakers.ui.navigation.MainNavigationRoute
import fr.paug.androidmakers.ui.viewmodel.Lce

/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(aboutActions: AboutActions, user: User?) {
  val mainNavController = rememberNavController()
  MainNavHost(
      mainNavController = mainNavController,
      onSessionClick = { sessionId, roomId, startTimestamp, endTimestamp ->
        mainNavController.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId/$roomId/$startTimestamp/$endTimestamp")
      },
      aboutActions = aboutActions,
      user = user,
      navigateToSpeakerDetails = { speakerId ->
        mainNavController.navigate("${MainNavigationRoute.SPEAKER_DETAIL.name}/$speakerId")
      },
  )
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    aboutActions: AboutActions,
    user: User?,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {
  NavHost(
      navController = mainNavController,
      startDestination = MainNavigationRoute.AVA.name
  ) {

    composable(route = MainNavigationRoute.AVA.name) {
      AVALayout(
          onSessionClick = onSessionClick,
          aboutActions = aboutActions,
          user = user,
          navigateToSpeakerDetails = navigateToSpeakerDetails
      )
    }

    composable(route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}/{roomId}/{startTimestamp}/{endTimestamp}") { backStackEntry ->
      val sessionId = backStackEntry.arguments!!.getString("sessionId")!!
      val roomId = backStackEntry.arguments!!.getString("roomId")!!
      val startTimestamp = backStackEntry.arguments!!.getString("startTimestamp")!!.toLong()
      val endTimestamp = backStackEntry.arguments!!.getString("endTimestamp")!!.toLong()
      val sessionDetailViewModel: SessionDetailViewModel = viewModel(
          factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
              @Suppress("UNCHECKED_CAST")
              return SessionDetailViewModel(
                  sessionId = sessionId,
                  roomId = roomId,
                  startTimestamp = startTimestamp,
                  endTimestamp = endTimestamp
              ) as T
            }
          }
      )
      val sessionDetailState by sessionDetailViewModel.sessionDetailState.collectAsState(
          initial = Lce.Loading
      )
      SessionDetailLayout(
          sessionDetailState = sessionDetailState,
          onBackClick = { mainNavController.popBackStack() },
          onBookmarkClick = { bookmarked -> sessionDetailViewModel.bookmark(bookmarked) },
      )
    }

    composable(route = "${MainNavigationRoute.SPEAKER_DETAIL.name}/{speakerId}") { backStackEntry ->
      val speakerId = backStackEntry.arguments!!.getString("speakerId")!!

      val speakerDetailsViewModel: SpeakerDetailsViewModel = viewModel(
          factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
              @Suppress("UNCHECKED_CAST")
              return SpeakerDetailsViewModel(
                  speakerId = speakerId
              ) as T
            }
          }
      )

      SpeakerDetailsRoute(
          speakerDetailsViewModel = speakerDetailsViewModel,
          onBackClick = { mainNavController.popBackStack() },
      )
    }

  }
}

@Preview
@Composable
private fun MainLayoutPreview() {
  MainLayout(aboutActions = AboutActions(), user = null)
}

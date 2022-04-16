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
import fr.paug.androidmakers.ui.navigation.MainNavigationRoute
import fr.paug.androidmakers.ui.viewmodel.Lce

/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(aboutActions: AboutActions) {
  val mainNavController = rememberNavController()
  MainNavHost(
      mainNavController = mainNavController,
      onSessionClick = { sessionId, roomId, startTimestamp, endTimestamp ->
        mainNavController.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId/$roomId/$startTimestamp/$endTimestamp")
      },
      aboutActions
  )
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
    aboutActions: AboutActions,
) {
  NavHost(mainNavController, startDestination = MainNavigationRoute.AVA.name) {
    composable(route = MainNavigationRoute.AVA.name) {
      AVALayout(onSessionClick = onSessionClick, aboutActions = aboutActions)
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
      val sessionDetailState by sessionDetailViewModel.sessionDetailState.collectAsState(initial = Lce.Loading)
      SessionDetailLayout(
          sessionDetailState = sessionDetailState,
          onBackClick = { mainNavController.popBackStack() },
          onBookmarkClick = { bookmarked -> sessionDetailViewModel.bookmark(bookmarked) },
      )
    }
  }
}

@Preview
@Composable
private fun MainLayoutPreview() {
  MainLayout(aboutActions = AboutActions())
}

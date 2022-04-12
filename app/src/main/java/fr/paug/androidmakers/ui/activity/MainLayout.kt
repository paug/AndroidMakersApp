package fr.paug.androidmakers.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.paug.androidmakers.ui.navigation.MainNavigationRoute

/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(aboutActions: AboutActions) {
    val mainNavController = rememberNavController()
    MainNavHost(
        mainNavController = mainNavController,
        onSessionClick = { sessionId ->
            mainNavController.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId")
        },
        aboutActions
    )
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    onSessionClick: (sessionId: String) -> Unit,
    aboutActions: AboutActions,
) {
    NavHost(mainNavController, startDestination = MainNavigationRoute.AVA.name) {
        composable(route = MainNavigationRoute.AVA.name) {
            AVALayout(onSessionClick = onSessionClick, aboutActions = aboutActions)
        }
        composable(route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}") { backStackEntry ->
            SessionDetailLayout(sessionId = backStackEntry.arguments!!.getString("sessionId")!!)
        }
    }
}

@Preview
@Composable
private fun MainLayoutPreview() {
    MainLayout(aboutActions = AboutActions())
}

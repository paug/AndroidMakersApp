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
fun MainLayout() {
    val mainNavController = rememberNavController()
    MainNavHost(
        mainNavController = mainNavController,
        onSessionClick = { sessionId ->
            mainNavController.navigate("${MainNavigationRoute.SESSION_DETAIL.name}/$sessionId")
        }
    )
}

@Composable
private fun MainNavHost(
    mainNavController: NavHostController,
    onSessionClick: (sessionId: String) -> Unit
) {
    NavHost(mainNavController, startDestination = MainNavigationRoute.AVA.name) {
        composable(route = MainNavigationRoute.AVA.name) {
            AVALayout(onSessionClick = onSessionClick)
        }
        composable(route = "${MainNavigationRoute.SESSION_DETAIL.name}/{sessionId}") { backStackEntry ->
            SessionDetailLayout(sessionId = backStackEntry.arguments!!.getString("sessionId")!!)
        }
    }
}


@Preview
@Composable
private fun MainLayoutPreview() {
    MainLayout()
}

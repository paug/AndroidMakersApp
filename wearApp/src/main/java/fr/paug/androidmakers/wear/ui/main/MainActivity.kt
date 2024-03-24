package fr.paug.androidmakers.wear.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.pager.PagerScreen
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.session.list.SessionListScreen
import fr.paug.androidmakers.wear.ui.settings.SettingsScreen
import fr.paug.androidmakers.wear.ui.signin.SignInScreen
import fr.paug.androidmakers.wear.ui.theme.AndroidMakersWearTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      installSplashScreen()
      WearApp()
      setTheme(android.R.style.Theme_DeviceDefault)
    }
  }
}

@Composable
fun WearApp(
    viewModel: MainViewModel = koinViewModel(),
) {
  val navController = rememberSwipeDismissableNavController()
  val onSignInClick: () -> Unit = {
    navController.navigate(Navigation.SIGN_IN)
  }
  val onSignInDismissOrTimeout: () -> Unit = {
    navController.popBackStack()
  }
  val onSignInSuccess = viewModel::onSignInSuccess
  AndroidMakersWearTheme {
    AppScaffold {
      SwipeDismissableNavHost(navController = navController, startDestination = Navigation.MAIN) {
        composable(Navigation.MAIN) {
          MainScreen(
              onSignInClick = onSignInClick,
              onSignOutClick = { viewModel.signOut() },
              viewModel = viewModel,
          )
        }
        composable(Navigation.SIGN_IN) {
          SignInScreen(
              onSignInSuccess = onSignInSuccess,
              onDismissOrTimeout = onSignInDismissOrTimeout
          )
        }
      }
    }
  }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
) {
  ScreenScaffold {
    val pagerState: PagerState = rememberPagerState(initialPage = viewModel.getConferenceDay() + 1, pageCount = { 3 })
    val user: User? by viewModel.user.collectAsState()
    val sessionsDay1: List<UISession>? by viewModel.sessionsDay1.collectAsState(initial = null)
    val sessionsDay2: List<UISession>? by viewModel.sessionsDay2.collectAsState(initial = null)

    PagerScreen(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
      when (page) {
        0 -> {
          SettingsScreen(
              user = user,
              onSignInClick = onSignInClick,
              onSignOutInClick = onSignOutClick,
          )
        }

        1 -> {
          SessionListScreen(sessions = sessionsDay1, title = stringResource(id = R.string.main_day1))
        }

        2 -> {
          SessionListScreen(sessions = sessionsDay2, title = stringResource(id = R.string.main_day2))
        }
      }
    }
  }
}

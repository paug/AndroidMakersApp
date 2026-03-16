package fr.paug.androidmakers.wear.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.wear.compose.foundation.pager.PagerState
import androidx.wear.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.foundation.SwipeToDismissBoxState
import androidx.wear.compose.foundation.SwipeToDismissValue
import androidx.wear.compose.foundation.edgeSwipeToDismiss
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.pager.PagerScreen
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.session.UISession
import fr.paug.androidmakers.wear.ui.session.details.SessionDetailScreen
import fr.paug.androidmakers.wear.ui.session.details.SessionDetailViewModel
import fr.paug.androidmakers.wear.ui.session.list.SessionListScreen
import fr.paug.androidmakers.wear.ui.settings.SettingsScreen
import fr.paug.androidmakers.wear.ui.signin.SignInScreen
import fr.paug.androidmakers.wear.ui.theme.AndroidMakersWearTheme
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    setContent {
      WearApp(onSwipeToDismissRootLevel = { ActivityCompat.finishAffinity(this) })
    }
  }
}

@Composable
fun WearApp(
  viewModel: MainViewModel = koinViewModel(),
  onSwipeToDismissRootLevel: () -> Unit,
) {
  val navController = rememberSwipeDismissableNavController()
  val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
  val swipeDismissableNavHostState = rememberSwipeDismissableNavHostState(swipeToDismissBoxState)

  // Workaround so swipe to dismiss works on the root level
  // See https://slack-chats.kotlinlang.org/t/16230979/problem-changing-basicswipetodismiss-background-color-gt
  var currentRoute by remember { mutableStateOf("") }
  LaunchedEffect(swipeToDismissBoxState.currentValue) {
    if (swipeToDismissBoxState.currentValue == SwipeToDismissValue.Dismissed) {
      if (currentRoute == Navigation.main) {
        onSwipeToDismissRootLevel()
      }
    }
  }

  val onSignInClick: () -> Unit = {
    navController.navigate(Navigation.signIn)
  }
  val onSignInDismissOrTimeout: () -> Unit = {
    navController.popBackStack()
  }

  navController.currentBackStackEntryAsState().value?.let { backStackEntry ->
    currentRoute = backStackEntry.destination.route.orEmpty()
  }

  AndroidMakersWearTheme {
    AppScaffold {
      val isResumed by LocalLifecycleOwner.current.lifecycle.currentStateFlow.map { it == Lifecycle.State.RESUMED }
        .collectAsState(false)
      SwipeDismissableNavHost(
        navController = navController,
        startDestination = Navigation.main,
        state = swipeDismissableNavHostState,
      ) {
        composable(Navigation.main) {
          MainScreen(
            viewModel = viewModel,
            isResumed = isResumed,
            swipeToDismissBoxState = swipeToDismissBoxState,
            onSignInClick = onSignInClick,
            onSignOutClick = { viewModel.signOut() },
            onSessionClick = { sessionId ->
              navController.navigate(Navigation.sessionDetail(sessionId))
            }
          )
        }
        composable(Navigation.signIn) {
          SignInScreen(
            onDismissOrTimeout = onSignInDismissOrTimeout
          )
        }
        composable(
          Navigation.sessionDetail,
          arguments = listOf(navArgument(Navigation.id) { type = NavType.StringType })
        ) {
          val sessionId = requireNotNull(it.arguments?.getString(Navigation.id)) {
            "Session detail requires a '${Navigation.id}' argument"
          }
          val sessionDetailViewModel: SessionDetailViewModel =
            koinViewModel { parametersOf(sessionId) }

          SessionDetailScreen(sessionDetailViewModel)
        }
      }
    }
  }
}

@Composable
fun MainScreen(
  viewModel: MainViewModel,
  isResumed: Boolean,
  swipeToDismissBoxState: SwipeToDismissBoxState,
  onSignInClick: () -> Unit,
  onSignOutClick: () -> Unit,
  onSessionClick: (String) -> Unit,
) {
  val user: User? by viewModel.user.collectAsStateWithLifecycle()
  val days: List<WearDaySchedule>? by viewModel.days.collectAsStateWithLifecycle()
  val sessionsDay1: List<UISession>? by viewModel.sessionsDay1.collectAsState(initial = null)
  val sessionsDay2: List<UISession>? by viewModel.sessionsDay2.collectAsState(initial = null)

  val pagerState: PagerState = rememberPagerState(
    initialPage = 0,
    pageCount = { 1 + (days?.size ?: 0) }
  )

  // Scroll to the current conference day once days are loaded
  LaunchedEffect(days) {
    val loadedDays = days
    if (loadedDays != null && loadedDays.isNotEmpty()) {
      val targetPage = viewModel.getConferenceDay(loadedDays) + 1
      if (pagerState.currentPage == 0) {
        pagerState.scrollToPage(targetPage)
      }
    }
  }

  PagerScreen(
    modifier = Modifier
      .fillMaxSize()
      .edgeSwipeToDismiss(swipeToDismissBoxState),
    state = pagerState,
    beyondViewportPageCount = 2,
  ) { page ->
    when (page) {
      0 -> {
        SettingsScreen(
          user = user,
          onSignInClick = onSignInClick,
          onSignOutClick = onSignOutClick,
          onRefreshClick = viewModel::refresh
        )
      }

      1 -> {
        SessionListScreen(
          sessions = sessionsDay1,
          title = stringResource(id = R.string.main_day1),
          isResumed = isResumed,
          onSessionClick = onSessionClick
        )
      }

      2 -> {
        SessionListScreen(
          sessions = sessionsDay2,
          title = stringResource(id = R.string.main_day2),
          isResumed = isResumed,
          onSessionClick = onSessionClick
        )
      }
    }
  }
}

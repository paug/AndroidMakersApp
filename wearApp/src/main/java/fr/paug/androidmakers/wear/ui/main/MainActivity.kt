@file:OptIn(ExperimentalHorologistApi::class, ExperimentalWearFoundationApi::class)

package fr.paug.androidmakers.wear.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TitleCard
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.AppScaffold
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.ItemType
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.AlertContent
import com.google.android.horologist.compose.material.Button
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.pager.PagerScreen
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.session.list.SessionListScreen
import fr.paug.androidmakers.wear.ui.signin.SignInScreen
import fr.paug.androidmakers.wear.ui.theme.AndroidMakersWearTheme
import org.koin.androidx.compose.koinViewModel

private val TAG = MainActivity::class.java.simpleName

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
fun WearApp() {
  val navController = rememberSwipeDismissableNavController()
  AndroidMakersWearTheme {
    AppScaffold {
      SwipeDismissableNavHost(navController = navController, startDestination = Navigation.MAIN) {
        composable(Navigation.MAIN) {
          MainScreen(
              onSignInClick = { navController.navigate(Navigation.SIGN_IN) },
              onSignOutClick = { TODO() }
          )
        }
        composable("list") {
          ListScreen()
        }
        composable(Navigation.SIGN_IN) {
          SignInScreen(onDismissOrTimeout = navController::popBackStack)
        }
      }
    }
  }
}


@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
) {
  ScreenScaffold {
    val pagerState: PagerState = rememberPagerState(initialPage = 1, pageCount = { 3 })
    PagerScreen(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
      when (page) {
        0 -> {
          val user: User? by viewModel.user.collectAsState()
          SettingsScreen(
              user = user,
              onSignInClick = onSignInClick,
              onSignOutInClick = onSignOutClick,
          )
        }

        1 -> {
          val sessionsDay1: List<SessionDetails>? by viewModel.sessionsDay1.collectAsState(initial = null)
          SessionListScreen(sessions = sessionsDay1)
        }

        2 -> {
          val sessionsDay2: List<SessionDetails>? by viewModel.sessionsDay2.collectAsState(initial = null)
          SessionListScreen(sessions = sessionsDay2)
        }
      }
    }
  }
}

@Composable
private fun SettingsScreen(
    user: User?,
    onSignInClick: () -> Unit,
    onSignOutInClick: () -> Unit,
) {
  Box(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
      contentAlignment = Alignment.Center
  ) {
    if (user == null) {
      Chip(label = stringResource(R.string.main_signIn), onClick = onSignInClick)
    } else {
      Chip(label = stringResource(R.string.main_signOut), onClick = onSignOutInClick)
    }
  }
}

@Composable
fun ListScreen() {
  var showDialog by remember { mutableStateOf(false) }

  /*
   * Specifying the types of items that appear at the start and end of the list ensures that the
   * appropriate padding is used.
   */
  val columnState = rememberResponsiveColumnState(
      contentPadding = ScalingLazyColumnDefaults.padding(
          first = ItemType.Text,
          last = ItemType.SingleButton
      )
  )

  ScreenScaffold(scrollState = columnState) {
    /*
     * The Horologist [ScalingLazyColumn] takes care of the horizontal and vertical
     * padding for the list, so there is no need to specify it, as in the [GreetingScreen]
     * composable.
     */
    ScalingLazyColumn(
        columnState = columnState,
        modifier = Modifier
            .fillMaxSize()
    ) {
      item {
        ResponsiveListHeader(contentPadding = firstItemPadding()) {
          Text(text = "Header")
        }
      }
      item {
        TitleCard(title = { Text("Example Title") }, onClick = { }) {
          Text("Example Content\nMore Lines\nAnd More")
        }
      }
      item {
        Chip(label = "Example Chip", onClick = { })
      }
      item {
        Button(
            imageVector = Icons.Default.Build,
            contentDescription = "Example Button",
            onClick = { showDialog = true }
        )
      }
    }
  }

  SampleDialog(
      showDialog = showDialog,
      onDismiss = { showDialog = false },
      onCancel = {},
      onOk = {}
  )
}

@Composable
fun Greeting(greetingName: String) {
  ResponsiveListHeader(contentPadding = firstItemPadding()) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = "Hello"
    )
  }
}

@Composable
fun SampleDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onOk: () -> Unit
) {
  val state = rememberResponsiveColumnState()

  Dialog(
      showDialog = showDialog,
      onDismissRequest = onDismiss,
      scrollState = state.state
  ) {
    SampleDialogContent(onCancel, onDismiss, onOk)
  }
}

@Composable
fun SampleDialogContent(
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    onOk: () -> Unit
) {
  AlertContent(
      icon = {},
      title = "Title",
      onCancel = {
        onCancel()
        onDismiss()
      },
      onOk = {
        onOk()
        onDismiss()
      }
  ) {
    item {
      Text(text = "An unknown error occurred during the request.")
    }
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun GreetingScreenPreview() {
  MainScreen(
      onSignInClick = {},
      onSignOutClick = {},
  )
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun ListScreenPreview() {
  ListScreen()
}

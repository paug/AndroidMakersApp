@file:OptIn(ExperimentalHorologistApi::class, ExperimentalWearFoundationApi::class)

package fr.paug.androidmakers.wear.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.theme.AndroidMakersWearTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel: MainViewModel by viewModel()
    viewModel.logAgenda()

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
      SwipeDismissableNavHost(navController = navController, startDestination = "menu") {
        composable("menu") {
          GreetingScreen(
              "Android",
              onShowList = { navController.navigate("list") }
          )
        }
        composable("list") {
          ListScreen()
        }
      }
    }
  }
}

@Composable
fun GreetingScreen(greetingName: String, onShowList: () -> Unit) {
  val scrollState = ScrollState(0)

  /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
   * version of LazyColumn for wear devices with some added features. For more information,
   * see d.android.com/wear/compose.
   */
  ScreenScaffold(scrollState = scrollState) {
    val padding = ScalingLazyColumnDefaults.padding(
        first = ItemType.Text,
        last = ItemType.Chip
    )()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .rotaryWithScroll(scrollState)
            .padding(padding),
        verticalArrangement = Arrangement.Center
    ) {
      Greeting(greetingName = greetingName)
      Chip(label = "Show List", onClick = onShowList)
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
        text = stringResource(R.string.hello_world, greetingName)
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
  GreetingScreen("Preview Android", onShowList = {})
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun ListScreenPreview() {
  ListScreen()
}

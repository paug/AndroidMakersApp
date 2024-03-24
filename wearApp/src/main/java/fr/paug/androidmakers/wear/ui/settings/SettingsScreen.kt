@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.AlertContent
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.wear.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    user: User?,
    onSignInClick: () -> Unit,
    onSignOutInClick: () -> Unit,
) {
  var showSignOutConfirmDialog by remember { mutableStateOf(false) }
  val columnState = rememberResponsiveColumnState()
  ScalingLazyColumn(
      columnState = columnState,
      modifier = Modifier.fillMaxSize()
  ) {
    item {
      if (user == null) {
        Chip(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.settings_signIn)) },
            onClick = onSignInClick
        )
      } else {
        Chip(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.settings_signOut)) },
            onClick = {
              showSignOutConfirmDialog = true
            }
        )
      }
    }

    item {
      val showOnlyBookmarkedSessions: Boolean by viewModel.showOnlyBookmarkedSessions.collectAsStateWithLifecycle()
      ToggleChip(
          modifier = Modifier.fillMaxWidth(),
          checked = showOnlyBookmarkedSessions,
          onCheckedChange = { checked ->
            viewModel.setShowOnlyBookmarkedSessions(checked)
          },
          label = { Text(stringResource(R.string.settings_showBookmarksOnly)) },
          toggleControl = {
            Switch(
                checked = showOnlyBookmarkedSessions,
                enabled = true,
            )
          },
      )
    }
  }

  SignOutConfirmDialog(
      showDialog = showSignOutConfirmDialog,
      onOk = {
        onSignOutInClick()
        showSignOutConfirmDialog = false
      },
      onCancel = {
        showSignOutConfirmDialog = false
      },
  )
}

@Composable
private fun SignOutConfirmDialog(
    showDialog: Boolean,
    onOk: () -> Unit,
    onCancel: () -> Unit,
) {
  Dialog(
      showDialog = showDialog,
      onDismissRequest = onCancel,
  ) {
    AlertContent(
        onOk = {
          onOk()
        },
        onCancel = {
          onCancel()
        },
    ) {
      item {
        Text(text = "Sign out?")
      }
    }
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun SettingsScreenPreview() {
  SettingsScreen(
      user = null,
      onSignInClick = {},
      onSignOutInClick = {},
  )
}

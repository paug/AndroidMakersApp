package fr.paug.androidmakers.ui.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowRationalPermissionDialog(
    packageName: String,
    openDialog: MutableState<Boolean>,
    onclick: () -> Unit,
) {
  if (openDialog.value) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        title = { Text(text = stringResource(id = R.string.permission_notifications_rationale_title)) },
        text = { Text(text = stringResource(id = R.string.permission_notifications_rationale_text)) },
        confirmButton = {
          TextButton(
              onClick = {
                openDialog.value = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                context.startActivity(intent)
              }
          ) {
            Text(stringResource(id = R.string.permission_notifications_rationale_positive))
          }
        }
    )
  }
}

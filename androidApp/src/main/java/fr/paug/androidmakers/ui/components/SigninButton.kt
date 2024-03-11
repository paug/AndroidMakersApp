package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.ui.LocalActivity
import fr.paug.androidmakers.ui.MR
import fr.paug.androidmakers.ui.util.stringResource


@Composable
fun SigninButton(user: User?) {
  val activity = LocalActivity.current
  val expandedState = remember { mutableStateOf(false) }

  IconButton(
      onClick = {
        expandedState.value = true
      }
  ) {
    if (user == null) {
      Icon(
          imageVector = Icons.Rounded.AccountCircle,
          contentDescription = stringResource(MR.strings.signin)
      )
    } else {
      AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
              .data(user.photoUrl)
              .build(),
          modifier = Modifier.clip(CircleShape),
          contentDescription = stringResource(MR.strings.signout)
      )
    }
  }

  DropdownMenu(
      expanded = expandedState.value,
      onDismissRequest = { expandedState.value = false }
  ) {
    if (user == null) {
      DropdownMenuItem(
          text = {
            Text(stringResource(MR.strings.signin))
          },
          onClick = {
            expandedState.value = false
            activity.signin()
          }
      )
    } else {
      DropdownMenuItem(
          text = {
            Text(stringResource(MR.strings.signout))
          },
          onClick = {
            expandedState.value = false
            activity.signout()
          }
      )
    }
  }
}

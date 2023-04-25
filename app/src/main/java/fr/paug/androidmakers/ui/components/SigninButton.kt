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
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.AMUser
import fr.paug.androidmakers.ui.LocalActivity


@Composable
fun SigninButton(user: AMUser?) {
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
          contentDescription = stringResource(R.string.signin)
      )
    } else {
      AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
              .data(user.photoUrl)
              .build(),
          modifier = Modifier.clip(CircleShape),
          contentDescription = stringResource(R.string.signout)
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
            Text(stringResource(id = R.string.signin))
          },
          onClick = {
            expandedState.value = false
            activity.signIn()
          }
      )
    } else {
      DropdownMenuItem(
          text = {
            Text(stringResource(id = R.string.signout))
          },
          onClick = {
            expandedState.value = false
            activity.signOut()
          }
      )
    }
  }
}
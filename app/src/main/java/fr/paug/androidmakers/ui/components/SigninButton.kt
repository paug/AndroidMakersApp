package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import fr.paug.androidmakers.ui.activity.AMUser
import fr.paug.androidmakers.ui.activity.LocalActivity


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
          imageVector = Icons.Filled.Person,
          contentDescription = stringResource(R.string.login)
      )
    } else {
      AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
              .data(user.photoUrl)
              .build(),
          modifier = Modifier.clip(CircleShape),
          contentDescription = stringResource(R.string.logout)
      )
    }
  }

  DropdownMenu(
      expanded = expandedState.value,
      onDismissRequest = { expandedState.value = false }
  ) {
    if (user == null) {
      DropdownMenuItem(onClick = {
        expandedState.value = false
        activity.signin()
      }) {
        Text(stringResource(id = R.string.login))
      }
    } else {
      DropdownMenuItem(onClick = {
        expandedState.value = false
        activity.signout()
      }) {
        Text(stringResource(id = R.string.logout))
      }
    }
  }
}
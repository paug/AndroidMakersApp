package com.androidmakers.ui.common

import androidx.compose.foundation.Image
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
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.User
import fr.paug.androidmakers.ui.MR

@Composable
actual fun SigninButton(
  user: User?,
  callbacks: SigninCallbacks,
) {
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
      Image(
        modifier = Modifier.clip(CircleShape),
        painter = rememberImagePainter(user.photoUrl.orEmpty()),
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
          callbacks.signin()
        }
      )
    } else {
      DropdownMenuItem(
        text = {
          Text(stringResource(MR.strings.signout))
        },
        onClick = {
          expandedState.value = false
          callbacks.signout()
        }
      )
    }
  }
}

package com.androidmakers.ui.common

import androidx.compose.runtime.Composable
import fr.androidmakers.domain.model.User

class SigninCallbacks(
  val signin: () -> Unit,
  val signout: () -> Unit,
)

@Composable
expect fun SigninButton(
  user: User?,
  callbacks: SigninCallbacks,
)

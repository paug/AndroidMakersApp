package com.androidmakers.ui.common

import androidx.compose.runtime.Composable
import fr.androidmakers.domain.model.User

@Composable
actual fun SigninButton(
  user: User?,
  callbacks: SigninCallbacks
) {
  // No-op: sign-in not supported on iOS
}

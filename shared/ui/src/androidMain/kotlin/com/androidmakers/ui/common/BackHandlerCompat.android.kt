package com.androidmakers.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackHandlerCompat(enabled: Boolean, onBack: () -> Unit) {
  BackHandler(enabled = enabled, onBack = onBack)
}

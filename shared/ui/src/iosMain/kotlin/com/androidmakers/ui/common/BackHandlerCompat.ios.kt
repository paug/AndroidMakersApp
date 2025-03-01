package com.androidmakers.ui.common

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandlerCompat(enabled: Boolean, onBack: () -> Unit) {
  // No-op on iOS
}

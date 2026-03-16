package com.androidmakers.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.androidmakers.domain.PlatformContext

@Composable
actual fun getPlatformContext(): PlatformContext = LocalContext.current

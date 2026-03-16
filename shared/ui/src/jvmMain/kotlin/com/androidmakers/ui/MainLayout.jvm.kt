package com.androidmakers.ui

import androidx.compose.runtime.Composable
import fr.androidmakers.domain.PlatformContext

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext.INSTANCE

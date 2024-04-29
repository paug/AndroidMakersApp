package com.androidmakers.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.androidmakers.domain.PlatformContext
import moe.tlaster.precompose.navigation.transition.NavTransition

actual val defaultTransition: NavTransition = NavTransition()

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalContext.current)

package com.androidmakers.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import fr.androidmakers.domain.PlatformContext

actual val defaultEnterTransition: EnterTransition = slideInHorizontally(initialOffsetX = { it })
actual val defaultExitTransition: ExitTransition = slideOutHorizontally(targetOffsetX = { -it / 4 })
actual val defaultPopEnterTransition: EnterTransition = slideInHorizontally(initialOffsetX = { -it / 4 })
actual val defaultPopExitTransition: ExitTransition = slideOutHorizontally(targetOffsetX = { it })

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext.INSTANCE

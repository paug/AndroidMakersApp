package com.androidmakers.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import fr.androidmakers.domain.PlatformContext

actual val defaultEnterTransition: EnterTransition = fadeIn() + scaleIn(initialScale = 0.9f)
actual val defaultExitTransition: ExitTransition = fadeOut() + scaleOut(targetScale = 1.1f)
actual val defaultPopEnterTransition: EnterTransition = fadeIn() + scaleIn(initialScale = 1.1f)
actual val defaultPopExitTransition: ExitTransition = fadeOut() + scaleOut(targetScale = 0.9f)

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext(LocalContext.current)

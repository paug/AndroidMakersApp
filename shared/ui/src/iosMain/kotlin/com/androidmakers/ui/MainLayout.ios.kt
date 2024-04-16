package com.androidmakers.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import moe.tlaster.precompose.navigation.transition.NavTransition

actual val defaultTransition: NavTransition = NavTransition(
  createTransition = slideInHorizontally { it },
  pauseTransition = slideOutHorizontally { -it / 4 },
  destroyTransition = slideOutHorizontally { it },
  resumeTransition = slideInHorizontally { -it / 4 },
  exitTargetContentZIndex = 1f
)

package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TODO to be changed
object AMColor {
  val amBlue = Color(0xff4eb6e3)
  val amBlueNavy = Color(0xff0014e6)
  val amRed = Color(0xffff5641)
  val bookmarked = amRed
}

@Composable
fun AndroidMakersTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorSchemeColors = if (!useDarkTheme) LightDefaultColorScheme else DarkDefaultColorScheme
  MaterialTheme(
    colorScheme = colorSchemeColors,
    typography = androidMakersTypography(),
    content = content,
  )
}

class AMAlphas(
  val small: Float,
  val big: Float
)

val AMAlpha = AMAlphas(
  15f / 255,
  50f / 255
)

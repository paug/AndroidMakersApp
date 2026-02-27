package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AMColor {
  val amPurple = Color(0xFF624BB1)
  val amPurpleLight = Color(0xFFEDEAF4)
  val amRed = Color(0xFFFF5641)
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

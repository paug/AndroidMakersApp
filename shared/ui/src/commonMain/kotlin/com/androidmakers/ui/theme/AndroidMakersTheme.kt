package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AMColor {
  val amPurple = Color(0xFF624BB1)
  val amPurpleLight = Color(0xFFEDEAF4)
  val amRed = Color(0xFFFF5641)
  val bookmarked = amRed
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AndroidMakersTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorSchemeColors = if (!useDarkTheme) LightDefaultColorScheme else DarkDefaultColorScheme
  MaterialExpressiveTheme(
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

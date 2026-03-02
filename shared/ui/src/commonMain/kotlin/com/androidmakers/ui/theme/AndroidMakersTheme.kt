package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AMColor {
  val amGreen = Color(0xFF00E676)
  val amGreenLight = Color(0xFFE8F5E9)
  val amRed = Color(0xFFFF5641)
  val bookmarked = amRed
}

val AndroidMakersShapes = Shapes(
  extraSmall = RoundedCornerShape(4.dp),
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(12.dp),
  large = RoundedCornerShape(16.dp),
  extraLarge = RoundedCornerShape(24.dp),
)

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
    shapes = AndroidMakersShapes,
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

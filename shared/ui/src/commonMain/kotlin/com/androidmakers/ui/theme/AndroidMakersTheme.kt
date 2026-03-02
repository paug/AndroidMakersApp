package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.androidmakers.domain.model.ThemePreference

val LocalIsDarkTheme = staticCompositionLocalOf { false }
val LocalIsNeobrutalism = staticCompositionLocalOf { false }

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

val NeoBrutalShapes = Shapes(
  extraSmall = RoundedCornerShape(0.dp),
  small = RoundedCornerShape(0.dp),
  medium = RoundedCornerShape(0.dp),
  large = RoundedCornerShape(0.dp),
  extraLarge = RoundedCornerShape(0.dp),
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AndroidMakersTheme(
  themePreference: ThemePreference = ThemePreference.System,
  content: @Composable () -> Unit
) {
  val isDark = when (themePreference) {
    ThemePreference.System -> isSystemInDarkTheme()
    ThemePreference.Light -> false
    ThemePreference.Dark -> true
    ThemePreference.Neobrutalism -> false
  }
  val colorScheme = when (themePreference) {
    ThemePreference.Neobrutalism -> NeoBrutalColorScheme
    else -> if (isDark) DarkDefaultColorScheme else LightDefaultColorScheme
  }
  val isNeobrutalism = themePreference == ThemePreference.Neobrutalism
  val shapes = if (isNeobrutalism) NeoBrutalShapes else AndroidMakersShapes
  val typography = if (isNeobrutalism) neoBrutalTypography() else androidMakersTypography()
  CompositionLocalProvider(
    LocalIsDarkTheme provides isDark,
    LocalIsNeobrutalism provides isNeobrutalism,
  ) {
    MaterialExpressiveTheme(
      colorScheme = colorScheme,
      typography = typography,
      shapes = shapes,
      content = content,
    )
  }
}

class AMAlphas(
  val small: Float,
  val big: Float
)

val AMAlpha = AMAlphas(
  15f / 255,
  50f / 255
)

package fr.paug.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.paug.androidmakers.R

val GillSans = FontFamily(
    Font(R.font.gill_sans_light, FontWeight.Light),
)

val Inter = FontFamily(
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal)
)

object AMColor {
  val amBlue = Color(0xff4eb6e3)
  val amBlueNavy = Color(0xff0014e6)
  val amRed = Color(0xffff5641)
  val bookmarked = amRed
}

class AMAlphas(
    val small: Float,
    val big: Float
)

val AMAlpha = AMAlphas(
    15f / 255,
    50f / 255
)


val AndroidMakersTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = (0.5).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        letterSpacing = (0.5).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = (0.5).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = GillSans,
        fontSize = 22.sp,
        fontWeight = FontWeight.Light,
        letterSpacing = (0.5).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = (0.5).sp,
    ),
    titleSmall = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    labelLarge = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    labelMedium = TextStyle(
        fontFamily = GillSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = (0.5).sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (0.5).sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = (0.5).sp,
    )
)


@Composable
fun AndroidMakersTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

  val colorSchemeColors = if (!useDarkTheme) LightDefaultColorScheme else DarkDefaultColorScheme

  MaterialTheme(
      colorScheme = colorSchemeColors,
      typography = AndroidMakersTypography,
      content = content,
  )
}

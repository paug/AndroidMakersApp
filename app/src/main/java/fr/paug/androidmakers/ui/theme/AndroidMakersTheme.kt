package fr.paug.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import fr.paug.androidmakers.R
import java.util.Date

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

    titleLarge = TextStyle(
        fontFamily = GillSans,
//        color = AMColor.amBlue,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    displayLarge = TextStyle(
        fontFamily = GillSans
    ),
    displayMedium = TextStyle(
        fontFamily = GillSans
    ),
    displaySmall = TextStyle(
        fontFamily = GillSans
    ),
    headlineLarge = TextStyle(
        fontFamily = GillSans
    ),
    headlineMedium = TextStyle(
        fontFamily = GillSans
    ),
    headlineSmall = TextStyle(
        fontFamily = GillSans
    ),
    titleMedium = TextStyle(
        fontFamily = GillSans
    ),
    titleSmall = TextStyle(
        fontFamily = GillSans
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter
    ),
    bodySmall = TextStyle(
        fontFamily = Inter
    ),
    labelLarge = TextStyle(
        fontFamily = GillSans
    ),
    labelMedium = TextStyle(
        fontFamily = GillSans
    ),
)


@Composable
fun AndroidMakersTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

  val systemUiController = rememberSystemUiController()

  DisposableEffect(systemUiController) {
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = !useDarkTheme
    )

    onDispose {}
  }

  val colorSchemeColors = if (!useDarkTheme) LightDefaultColorScheme else DarkDefaultColorScheme

  MaterialTheme(
      colorScheme = colorSchemeColors,
      typography = AndroidMakersTypography,
      content = content,
  )
}

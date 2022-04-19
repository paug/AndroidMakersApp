package fr.paug.androidmakers.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import fr.paug.androidmakers.R

val GillSans = FontFamily(
    Font(R.font.gill_sans_light, FontWeight.Light),
)

object AMColor {
  val amBlue = Color(0xff1EB6E1)
  val amRed = Color(0xffE85145)
  val bookmarked = amRed
}

class AMAlphas(
    val small: Float,
    val big: Float
)

val AMAlpha = AMAlphas(
    15f/255,
    50f/255
)


@OptIn(ExperimentalUnitApi::class)
@Composable
fun AndroidMakersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    darkColors(
        primary = AMColor.amBlue,
        secondary = AMColor.amRed
    )
  } else {
    lightColors(
        primary = AMColor.amBlue,
        secondary = AMColor.amRed
    )
  }
  MaterialTheme(
      colors = colors,
      typography = Typography(
          h5 = TextStyle(
              fontFamily = GillSans,
              color = AMColor.amBlue,
              fontSynthesis = FontSynthesis.Weight,
              fontWeight = FontWeight.Bold,
              fontSize = TextUnit(24f, TextUnitType.Sp)
          )
      ),
      content = content,
  )
}

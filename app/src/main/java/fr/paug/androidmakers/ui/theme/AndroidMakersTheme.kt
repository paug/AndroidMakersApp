package fr.paug.androidmakers.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
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
  val lightGray = Color(0xffF0F0F0)
  val gray = Color(0xffbbbbbb)
  val bookmarked = Color(0xffE85145)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AndroidMakersTheme(content: @Composable () -> Unit) {
  MaterialTheme(
      colors = lightColors(
          primary = Color(0xff1EB6E1)
      ),
      typography = Typography(
          h5 = TextStyle(
              fontFamily = GillSans,
              color = Color(0xff1EB6E1),
              fontSynthesis = FontSynthesis.Weight,
              fontWeight = FontWeight.Bold,
              fontSize = TextUnit(24f, TextUnitType.Sp)
          )
      ),
      content = content
  )
}

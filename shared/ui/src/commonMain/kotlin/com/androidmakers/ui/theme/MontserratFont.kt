package com.androidmakers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.asFont
import fr.paug.androidmakers.ui.MR

@Composable
fun montserratRegular() = MR.fonts.montserrat_regular.asFont()!!

@Composable
fun montserratItalic() = MR.fonts.montserrat_italic.asFont(
  FontWeight.Normal,
  FontStyle.Italic
)!!

@Composable
fun montserratSemiBold() = MR.fonts.montserrat_semibold.asFont(
  FontWeight.SemiBold
)!!

@Composable
fun montserratSemiBoldItalic() = MR.fonts.montserrat_semibolditalic.asFont(
  FontWeight.SemiBold,
  FontStyle.Italic
)!!

@Composable
fun montserratBold() = MR.fonts.montserrat_bold.asFont(
  FontWeight.Bold
)!!

@Composable
fun montserratBoldItalic() = MR.fonts.montserrat_bolditalic.asFont(
  FontWeight.Bold,
  FontStyle.Italic
)!!

@Composable
fun montserratFamily() = FontFamily(
  montserratBold(),
  montserratBoldItalic(),
  montserratItalic(),
  montserratRegular(),
  montserratSemiBold(),
  montserratSemiBoldItalic()
)

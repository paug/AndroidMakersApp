package com.androidmakers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.asFont
import fr.paug.androidmakers.ui.MR


@Composable
fun montserratExtraLight() = MR.fonts.montserrat_extralight.asFont(
  FontWeight.ExtraLight
)!!

@Composable
fun montserratExtraLightItalic() = MR.fonts.montserrat_extralightitalic.asFont(
  FontWeight.ExtraLight, FontStyle.Italic
)!!

@Composable
fun montserratLight() = MR.fonts.montserrat_light.asFont(
  FontWeight.Light
)!!

@Composable
fun montserratLightItalic() = MR.fonts.montserrat_lightitalic.asFont(
  FontWeight.Light, FontStyle.Italic
)!!

@Composable
fun montserratThin() = MR.fonts.montserrat_thin.asFont(
  FontWeight.Thin
)!!

@Composable
fun montserratThinItalic() = MR.fonts.montserrat_thinitalic.asFont(
  FontWeight.Thin, FontStyle.Italic
)!!

@Composable
fun montserratRegular() = MR.fonts.montserrat_regular.asFont()!!

@Composable
fun montserratItalic() = MR.fonts.montserrat_italic.asFont(
  FontWeight.Normal,
  FontStyle.Italic
)!!

@Composable
fun montserratMedium() = MR.fonts.montserrat_medium.asFont(
  FontWeight.Medium
)!!

@Composable
fun montserratMediumItalic() = MR.fonts.montserrat_mediumitalic.asFont(
  FontWeight.Medium, FontStyle.Italic
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
fun montserratExtraBold() = MR.fonts.montserrat_extrabold.asFont(
  FontWeight.ExtraBold
)!!

@Composable
fun montserratExtraBoldItalic() = MR.fonts.montserrat_extrabolditalic.asFont(
  FontWeight.ExtraBold,
  FontStyle.Italic
)!!

@Composable
fun montserratBlack() = MR.fonts.montserrat_black.asFont(
  FontWeight.Black
)!!

@Composable
fun montserratBlackItalic() = MR.fonts.montserrat_blackitalic.asFont(
  FontWeight.Black,
  FontStyle.Italic
)!!

@Composable
fun montserratFamily() = FontFamily(
  montserratBlack(),
  montserratBlackItalic(),
  montserratBold(),
  montserratBoldItalic(),
  montserratExtraBold(),
  montserratExtraBoldItalic(),
  montserratExtraLight(),
  montserratExtraLightItalic(),
  montserratItalic(),
  montserratLight(),
  montserratLightItalic(),
  montserratMedium(),
  montserratMediumItalic(),
  montserratRegular(),
  montserratSemiBold(),
  montserratSemiBoldItalic(),
  montserratThin(),
  montserratThinItalic()
)

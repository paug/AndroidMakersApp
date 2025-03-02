package com.androidmakers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.montserrat_bold
import fr.paug.androidmakers.ui.montserrat_bolditalic
import fr.paug.androidmakers.ui.montserrat_italic
import fr.paug.androidmakers.ui.montserrat_regular
import fr.paug.androidmakers.ui.montserrat_semibold
import fr.paug.androidmakers.ui.montserrat_semibolditalic
import org.jetbrains.compose.resources.Font

@Composable
fun montserratFamily() = FontFamily(
  Font(
    resource = Res.font.montserrat_regular,
    weight = FontWeight.Normal,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.montserrat_italic,
    weight = FontWeight.Normal,
    style = FontStyle.Italic
  ),
  Font(
    resource = Res.font.montserrat_semibold,
    weight = FontWeight.SemiBold,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.montserrat_semibolditalic,
    weight = FontWeight.SemiBold,
    style = FontStyle.Italic
  ),
  Font(
    resource = Res.font.montserrat_bold,
    weight = FontWeight.Bold,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.montserrat_bolditalic,
    weight = FontWeight.Bold,
    style = FontStyle.Italic
  )
)

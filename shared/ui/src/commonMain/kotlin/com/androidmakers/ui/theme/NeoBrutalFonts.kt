package com.androidmakers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.roboto_bold
import fr.paug.androidmakers.ui.roboto_italic
import fr.paug.androidmakers.ui.roboto_medium
import fr.paug.androidmakers.ui.roboto_regular
import fr.paug.androidmakers.ui.spacegrotesk_bold
import fr.paug.androidmakers.ui.spacegrotesk_regular
import fr.paug.androidmakers.ui.spacegrotesk_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun spaceGroteskFamily() = FontFamily(
  Font(
    resource = Res.font.spacegrotesk_regular,
    weight = FontWeight.Normal,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.spacegrotesk_semibold,
    weight = FontWeight.SemiBold,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.spacegrotesk_bold,
    weight = FontWeight.Bold,
    style = FontStyle.Normal
  )
)

@Composable
fun robotoFamily() = FontFamily(
  Font(
    resource = Res.font.roboto_regular,
    weight = FontWeight.Normal,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.roboto_italic,
    weight = FontWeight.Normal,
    style = FontStyle.Italic
  ),
  Font(
    resource = Res.font.roboto_medium,
    weight = FontWeight.Medium,
    style = FontStyle.Normal
  ),
  Font(
    resource = Res.font.roboto_bold,
    weight = FontWeight.Bold,
    style = FontStyle.Normal
  )
)

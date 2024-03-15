@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class, ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package com.androidmakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import fr.paug.androidmakers.ui.MR
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
fun gillSansLight() = fontFamilyResource(MR.fonts.gill_sans_light)

@Composable
fun inter() = fontFamilyResource(MR.fonts.inter_regular)

@Composable
fun androidMakersTypography() = Typography(
    displayLarge = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = (0.5).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        letterSpacing = (0.5).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (0.5).sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = (0.5).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = gillSansLight(),
        fontSize = 22.sp,
        fontWeight = FontWeight.Light,
        letterSpacing = (0.5).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = (0.5).sp,
    ),
    titleSmall = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    labelLarge = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    labelMedium = TextStyle(
        fontFamily = gillSansLight(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = (0.5).sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = inter(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (0.5).sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = inter(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = (0.5).sp,
    ),
    bodySmall = TextStyle(
        fontFamily = inter(),
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
    PreComposeApp {
      MaterialTheme(
          colorScheme = colorSchemeColors,
          typography = androidMakersTypography(),
          content = content,
      )
    }

}

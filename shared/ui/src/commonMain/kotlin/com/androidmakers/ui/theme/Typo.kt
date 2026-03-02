package com.androidmakers.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun androidMakersTypography() = Typography(
  displayLarge = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Bold,
    fontSize = 57.sp,
  ),
  displayMedium = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Bold,
    fontSize = 45.sp,
  ),
  displaySmall = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Normal,
    fontSize = 36.sp,
  ),
  headlineLarge = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp,
  ),
  headlineMedium = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Bold,
    fontSize = 28.sp,
  ),
  headlineSmall = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
  ),
  titleLarge = TextStyle(
    fontFamily = montserratFamily(),
    fontSize = 22.sp,
    fontWeight = FontWeight.SemiBold,
  ),
  titleMedium = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
  ),
  titleSmall = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
  ),
  labelLarge = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
  ),
  labelMedium = TextStyle(
    fontFamily = montserratFamily(),
    fontSize = 12.sp,
  ),
  labelSmall = TextStyle(
    fontFamily = montserratFamily(),
    fontSize = 11.sp,
  ),
  bodyLarge = TextStyle(
    fontFamily = montserratFamily(),
    fontSize = 16.sp,
  ),
  bodyMedium = TextStyle(
    fontFamily = montserratFamily(),
    fontSize = 14.sp,
  ),
  bodySmall = TextStyle(
    fontFamily = montserratFamily(),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
  )
)

@Composable
fun neoBrutalTypography(): Typography {
  val spaceGrotesk = spaceGroteskFamily()
  val roboto = robotoFamily()

  return Typography(
    displayLarge = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.Bold,
      fontSize = 57.sp,
    ),
    displayMedium = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.Bold,
      fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.Normal,
      fontSize = 36.sp,
    ),
    headlineLarge = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.Bold,
      fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.Bold,
      fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.SemiBold,
      fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
      fontFamily = spaceGrotesk,
      fontSize = 22.sp,
      fontWeight = FontWeight.SemiBold,
    ),
    titleMedium = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.SemiBold,
      fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
      fontFamily = spaceGrotesk,
      fontWeight = FontWeight.SemiBold,
      fontSize = 14.sp,
    ),
    labelLarge = TextStyle(
      fontFamily = roboto,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
      fontFamily = roboto,
      fontWeight = FontWeight.Medium,
      fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
      fontFamily = roboto,
      fontSize = 11.sp,
    ),
    bodyLarge = TextStyle(
      fontFamily = roboto,
      fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
      fontFamily = roboto,
      fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
      fontFamily = roboto,
      fontWeight = FontWeight.Normal,
      fontSize = 12.sp,
    )
  )
}

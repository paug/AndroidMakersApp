package fr.paug.androidmakers.wear.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun AndroidMakersWearTheme(
    content: @Composable () -> Unit
) {
  MaterialTheme(
      colors = wearColorPalette,
      content = content
  )
}

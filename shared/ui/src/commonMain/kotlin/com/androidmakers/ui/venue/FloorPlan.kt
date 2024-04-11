package com.androidmakers.ui.venue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seiko.imageloader.rememberImagePainter

@Composable
fun FloorPlan(floorPlanUrl: String) {
  Box(
      modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
          .verticalScroll(rememberScrollState())
  ) {
    val painter = rememberImagePainter(floorPlanUrl)

    Image(
        painter = painter,
        contentDescription = "floor plan"
    )
  }
}

package com.androidmakers.ui.venue

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun FloorPlan(floorPlanUrl: String) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    AsyncImage(
      model = floorPlanUrl,
      contentDescription = "floor plan"
    )
  }
}

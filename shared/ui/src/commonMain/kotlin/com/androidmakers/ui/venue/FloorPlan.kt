package com.androidmakers.ui.venue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import fr.paug.androidmakers.ui.MR

@Composable
fun FloorPlan(floorPlanUrl: String) {
  Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
    val painter = rememberImagePainter(floorPlanUrl)

    Image(
        painter = painter,
        contentDescription = "floor plan"
    )
  }
}

package com.androidmakers.ui.venue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import fr.paug.androidmakers.ui.MR

@Composable
fun FloorPlan() {
  Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
    Image(
        painter = painterResource(MR.images.beffroi),
        contentDescription = "floor plan"
    )
  }
}

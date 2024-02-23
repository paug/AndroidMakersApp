package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fr.paug.androidmakers.R

@Composable
fun FloorPlan() {
  Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
    Image(
        painter = painterResource(id = R.drawable.beffroi1800px),
        contentDescription = "floor plan"
    )
  }
}

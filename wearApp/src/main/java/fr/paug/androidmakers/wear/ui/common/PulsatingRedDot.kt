package fr.paug.androidmakers.wear.ui.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.wear.ui.theme.amRed

@Composable
fun Pulsating(content: @Composable () -> Unit) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")

  val scale by infiniteTransition.animateFloat(
    label = "pulse",
    initialValue = 1f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(500),
      repeatMode = RepeatMode.Reverse
    )
  )

  Box(modifier = Modifier.alpha(scale)) {
    content()
  }
}

@Composable
fun PulsatingRedDot() {
  Pulsating {
    Box(
      modifier = Modifier
        .size(8.dp)
        .background(color = amRed, shape = CircleShape)
    )
  }
}

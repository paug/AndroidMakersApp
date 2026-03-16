package com.androidmakers.ui.theme

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.neoBrutalElevation(
    shadowOffset: Dp = 3.dp,
    borderWidth: Dp = 2.dp,
): Modifier {
    if (!LocalIsNeobrutalism.current) return this
    return this
        .drawWithContent {
            val offsetPx = shadowOffset.toPx()
            drawRect(
                color = Color.Black,
                topLeft = Offset(offsetPx, offsetPx),
                size = Size(size.width, size.height),
            )
            drawContent()
        }
        .border(borderWidth, Color.Black, RectangleShape)
}

@Composable
fun Modifier.neoBrutalBorder(
    borderWidth: Dp = 2.dp,
): Modifier {
    if (!LocalIsNeobrutalism.current) return this
    return this.border(borderWidth, Color.Black, RectangleShape)
}

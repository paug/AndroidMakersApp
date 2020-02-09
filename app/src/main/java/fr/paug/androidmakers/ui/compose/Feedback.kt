package fr.paug.androidmakers.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Canvas
import androidx.ui.layout.Container
import androidx.ui.layout.Padding
import androidx.ui.layout.Spacing
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.tooling.preview.Preview

@Composable
fun VoteCard() {
    MaterialTheme {
        Card(shape = RoundedCornerShape(5.dp)) {
            Container {
                Padding(padding = 5.dp) {
                    Text(text = "Hello Compose")
                }
            }
        }
    }
}

@Preview
@Composable
fun votePreview() {
    VoteCard()
}
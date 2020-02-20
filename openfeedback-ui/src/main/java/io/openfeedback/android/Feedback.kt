package io.openfeedback.android

import android.util.Log
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Draw
import androidx.ui.core.Text
import androidx.ui.foundation.Border
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.geometry.Offset
import androidx.ui.graphics.*
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.exp
import kotlin.random.Random

@Composable
fun OpenFeedback(votes: List<VoteModel>,
                 columnCount: Int = 2,
                 onModelClicked: (VoteModel) -> Unit
) {
    MaterialTheme {
        Column {
            CardsGrid(votes = votes, onModelClicked = onModelClicked, columnCount = columnCount)
            Container(modifier = LayoutWidth.Fill + LayoutPadding(top = 5.dp, bottom = 10.dp)) {
                PoweredBy()
            }
        }
    }
}

@Composable
fun PoweredBy() {
    Row {
        Text(text = "Powered by OpenFeedback",
                style = TextStyle(
                        color = Color(0, 0, 0, 200),
                        textAlign = TextAlign.Center
                )
        )
        // Uncomment when this does not make the compiler crash
        //DrawImage(image = imageResource(R.drawable.test))
    }
}

@Composable
fun CardsGrid(
        votes: List<VoteModel>,
        columnCount: Int = 2,
        onModelClicked: (VoteModel) -> Unit
) {
    Container(modifier = LayoutPadding(0.dp)) {
        Row {
            0.until(columnCount).forEach { column ->
                Container(modifier = LayoutFlexible(flex = 1f)) {
                    Column {
                        votes.filterIndexed { index, _ ->
                            index % columnCount == column
                        }.forEach { voteModel ->
                            Ripple(bounded = true) {
                                Clickable(consumeDownOnStart = false,
                                        onClick = {
                                            onModelClicked(voteModel)
                                        }) {
                                    VoteCard(voteModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VoteCard(voteModel: VoteModel) {
    val b = 240
    val border = if (voteModel.votedByUser) {
        4.dp
    } else {
        0.dp
    }

    Card(shape = RoundedCornerShape(5.dp),
            border = Border(border, Color.Gray),
            color = Color(b, b, b, 255),
            modifier = LayoutPadding(5.dp)
    ) {
        Container(height = 100.dp, expanded = true) {
            Draw { canvas, parentSize ->
                val paint = Paint()
                paint.style = PaintingStyle.fill
                paint.isAntiAlias = true

                voteModel.dots.forEach { dot ->
                    paint.color = Color(
                            dot.color.substring(0, 2).toInt(16),
                            dot.color.substring(2, 4).toInt(16),
                            dot.color.substring(4, 6).toInt(16),
                            255 / 3
                    )
                    val offset = Offset(
                            parentSize.width.value * dot.x,
                            parentSize.height.value * dot.y
                    )
                    canvas.drawCircle(offset, 30.dp.value, paint)
                }
            }
            Text(
                    modifier = LayoutPadding(10.dp),
                    text = voteModel.text,
                    style = TextStyle(
                            color = Color(0, 0, 0, 200),
                            textAlign = TextAlign.Center
                    )
            )
        }
    }
}

@Preview
@Composable
fun votePreview() {
    OpenFeedback(fakeVotes, 2) {}
}

val fakeVotes = listOf(
        fakeVoteModel("Drôle/original \uD83D\uDE03", 3),
        fakeVoteModel("Trèsenrichissant enrichissant enrichissant \uD83E\uDD13", 1),
        fakeVoteModel("Super intéressant \uD83D\uDC4D", 8),
        fakeVoteModel("Très bon orateur \uD83D\uDC4F", 21),
        fakeVoteModel("Pas clair \uD83E\uDDD0", 2)
)

fun dots(count: Int, possibleColors: List<String>): List<Dot> {
    return 0.until(count).map {
        Dot(Random.Default.nextFloat(),
                Random.Default.nextFloat(),
                possibleColors.get(Random.Default.nextInt().absoluteValue % possibleColors.size)
        )
    }
}

fun fakeVoteModel(text: String, count: Int): VoteModel {
    val color = listOf("7cebcd",
            "0822cb",
            "d5219d",
            "8eec1a")

    return VoteModel(id = Random.Default.nextInt().toString(), text = text, dots = dots(count, color), votedByUser = count % 2 == 0)
}
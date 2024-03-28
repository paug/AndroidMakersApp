package com.androidmakers.ui.agenda

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.model.UISession
import com.androidmakers.ui.theme.AMColor
import fr.androidmakers.domain.model.Session
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Composable
private fun maybeClickable(uiSession: UISession, onSessionClicked: ((UISession) -> Unit)): Modifier {
  return if (uiSession.isServiceSession.not()) {
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = false),
        onClick = {
          onSessionClicked.invoke(uiSession)
        }
    )
  } else {
    Modifier
  }
}

@Composable
fun backgroundColor(uiSession: UISession): Color {
  return if (uiSession.isServiceSession) {
    MaterialTheme.colorScheme.surface
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }
}

@Composable
fun AgendaRow(
    uiSession: UISession,
    modifier: Modifier = Modifier,
    onSessionClicked: (UISession) -> Unit,
    onSessionBookmarked: (UISession, Boolean) -> Unit,
) {
  ListItem(
      modifier = modifier,
      colors = ListItemDefaults.colors(
          containerColor = backgroundColor(uiSession),
      ),
      headlineContent = {
        Text(
            text = uiSession.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = maybeClickable(uiSession, onSessionClicked)
        )
      },
      supportingContent = {
        Column(
            modifier = maybeClickable(uiSession, onSessionClicked).padding(top = 12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
          val speakers = uiSession.speakers.joinToString(", ") { it.name }
          if (speakers.isNotBlank()) {
            Text(
                text = speakers,
                style = MaterialTheme.typography.bodyMedium,
            )
          }

          Text(
              text = uiSession.subtitle(),
              style = MaterialTheme.typography.bodyMedium,
              modifier = Modifier.padding(top = 4.dp)
          )
        }
      },
      trailingContent = {
        if (!uiSession.isServiceSession) {
          Box {
            val isBookmarked = uiSession.isFavorite
            val imageVector = if (isBookmarked) Icons.Rounded.BookmarkRemove
            else Icons.Rounded.BookmarkAdd

            val tint by animateColorAsState(
                if (isBookmarked) AMColor.bookmarked
                else Color.LightGray
            )

            IconToggleButton(
                checked = isBookmarked,
                onCheckedChange = {
                  onSessionBookmarked(uiSession, it)
                },
            ) {
              Icon(
                  imageVector = imageVector,
                  contentDescription = "favorite",
                  tint = tint
              )
            }
          }
        }
      },
      overlineContent = {
        // Nothing to do
      },
  )
}

private fun UISession.subtitle() = buildString {
  append(formattedDuration())
  append(" / $room")
  val emoji = EmojiUtils.getLanguageInEmoji(language)
  if (emoji != null) {
    append(" / $emoji")
  }
}

fun UISession.formattedDuration(): String {
  val duration = endDate - startDate

  return buildString {
    if (duration.inWholeHours > 0) {
      append(duration.inWholeHours)
      append("h")
    }

    append((duration - duration.inWholeHours.hours).inWholeMinutes)
    append("min")
  }
}

@Preview
@Composable
private fun AgendaRowPreview() {
  AgendaRow(fakeUiSession, onSessionClicked = {}, onSessionBookmarked = { _,_ -> })
}

private val fakeUiSession = UISession(
    id = "1",
    title = "Why did the chicken cross the road?",
    language = "french",
    speakers = listOf(UISession.Speaker("chicken1")),
    roomId = "1",
    room = "Moebius",
    startDate = Instant.parse("2022-04-25T09:00:00+02:00"),
    endDate = Instant.parse("2022-04-25T10:00:00+02:00"),
    isServiceSession = false,
    isFavorite = false
)

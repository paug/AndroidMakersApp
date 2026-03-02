package com.androidmakers.ui.agenda

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.model.UISession
import com.androidmakers.ui.theme.AMColor
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.session_app_clinic_apply
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration.Companion.hours

@Composable
internal fun ServiceSessionRow(
  session: UISession,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surfaceContainer,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = session.title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = session.formattedDuration(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.outline,
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SessionRow(
  uiSession: UISession,
  modifier: Modifier = Modifier,
  onSessionClick: (UISession) -> Unit,
  onSessionBookmark: (UISession, Boolean) -> Unit,
  onApplyForAppClinicClick: () -> Unit,
) {
  ElevatedCard(
    modifier = modifier,
    onClick = { onSessionClick(uiSession) },
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
  ) {
    val isBookmarked = uiSession.isFavorite
    val imageVector = if (isBookmarked) Icons.Rounded.BookmarkRemove
    else Icons.Rounded.BookmarkAdd
    val tint by animateColorAsState(
      if (isBookmarked) AMColor.bookmarked
      else MaterialTheme.colorScheme.outline
    )

    ListItem(
      colors = ListItemDefaults.colors(containerColor = Color.Transparent),
      overlineContent = {
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          uiSession.tags.forEach { tag ->
            TagChip(tag)
          }
          RoomChip(uiSession.room)
        }
      },
      headlineContent = {
        Text(
          text = uiSession.title,
          modifier = Modifier.padding(top = 4.dp),
          style = MaterialTheme.typography.titleMedium,
          maxLines = 2,
        )
      },
      supportingContent = {
        Column(modifier = Modifier.padding(top = 4.dp)) {
          val speakers = uiSession.speakers.joinToString(", ") { it.name }
          if (speakers.isNotBlank()) {
            Text(
              text = speakers,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }

          Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            DurationChip(uiSession.formattedDuration())

            val emoji = EmojiUtils.getLanguageInEmoji(uiSession.language)
            if (emoji != null) {
              Text(
                text = emoji,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.bodySmall,
              )
            }

            if (uiSession.isAppClinic) {
              Spacer(modifier = Modifier.width(8.dp))
              Button(
                onClick = onApplyForAppClinicClick,
              ) {
                Text(
                  text = stringResource(Res.string.session_app_clinic_apply),
                  style = MaterialTheme.typography.labelMedium,
                )
              }
            }
          }
        }
      },
      trailingContent = {
        IconToggleButton(
          modifier = Modifier.size(32.dp),
          checked = isBookmarked,
          onCheckedChange = { onSessionBookmark(uiSession, it) },
        ) {
          Icon(
            imageVector = imageVector,
            contentDescription = "favorite",
            tint = tint,
            modifier = Modifier.size(20.dp),
          )
        }
      },
    )
  }
}

@Composable
private fun TagChip(tag: String) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.secondaryContainer,
  ) {
    Text(
      text = tag,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSecondaryContainer,
    )
  }
}

@Composable
private fun RoomChip(room: String) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.primaryContainer,
  ) {
    Text(
      text = room,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
  }
}

@Composable
private fun DurationChip(duration: String) {
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surfaceContainer,
  ) {
    Text(
      text = duration,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurface,
    )
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
  SessionRow(
    fakeUiSession,
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
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
    isFavorite = false,
    tags = listOf("Kotlin", "Architecture"),
)

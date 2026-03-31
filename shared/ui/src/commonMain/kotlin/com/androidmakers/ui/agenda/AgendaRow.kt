package com.androidmakers.ui.agenda

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.model.UISession
import com.androidmakers.ui.theme.AMColor
import com.androidmakers.ui.theme.neoBrutalBorder
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.ic_bookmark_add
import fr.paug.androidmakers.ui.ic_bookmark_added
import fr.paug.androidmakers.ui.ic_location_on
import fr.paug.androidmakers.ui.ic_schedule
import fr.paug.androidmakers.ui.session_app_clinic_apply
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.hours

@Composable
internal fun ServiceSessionRow(
  session: UISession,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.neoBrutalElevation(),
    shape = MaterialTheme.shapes.large,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ),
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
    modifier = modifier.neoBrutalElevation(),
    onClick = { onSessionClick(uiSession) },
    shape = MaterialTheme.shapes.large,
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
  ) {
    val isBookmarked = uiSession.isFavorite
    val tint by animateColorAsState(
      if (isBookmarked) AMColor.bookmarked
      else MaterialTheme.colorScheme.outline
    )

    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // 1. Title + Bookmark
      Row(verticalAlignment = Alignment.Top) {
        Text(
          text = uiSession.title,
          modifier = Modifier.weight(1f),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurface,
          overflow = TextOverflow.Ellipsis,
        )
        IconToggleButton(
          modifier = Modifier.size(48.dp),
          checked = isBookmarked,
          onCheckedChange = { onSessionBookmark(uiSession, it) },
        ) {
          val bookmarkIcon = if (isBookmarked) {
            Res.drawable.ic_bookmark_added
          } else {
            Res.drawable.ic_bookmark_add
          }
          Icon(
            painter = painterResource(bookmarkIcon),
            contentDescription = "favorite",
            tint = tint,
            modifier = Modifier.size(24.dp),
          )
        }
      }

      // 2. Speakers
      val speakers = uiSession.speakers.joinToString(", ") { it.name }
      if (speakers.isNotBlank()) {
        Text(
          text = speakers,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      // 3. MetaRow — room, duration, language
      MetaRow(
        room = uiSession.room,
        duration = uiSession.formattedDuration(),
        language = uiSession.language,
      )

      // 4. Tags (max 3 visible + overflow indicator)
      if (uiSession.tags.isNotEmpty()) {
        val maxVisible = 3
        val visibleTags = uiSession.tags.take(maxVisible)
        val overflow = uiSession.tags.size - maxVisible
        FlowRow(
          modifier = Modifier.padding(top = 2.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          visibleTags.forEach { tag ->
            TagChip(tag)
          }
          if (overflow > 0) {
            TagChip("+$overflow")
          }
        }
      }

      // 5. App Clinic
      if (uiSession.isAppClinic) {
        FilledTonalButton(
          onClick = onApplyForAppClinicClick,
          modifier = Modifier.neoBrutalElevation(shadowOffset = 2.dp),
        ) {
          Text(
            text = stringResource(Res.string.session_app_clinic_apply),
            style = MaterialTheme.typography.labelMedium,
          )
        }
      }
    }
  }
}

@Composable
private fun TagChip(tag: String) {
  Surface(
    modifier = Modifier.neoBrutalBorder(),
    shape = MaterialTheme.shapes.small,
    color = MaterialTheme.colorScheme.surfaceContainerHighest,
  ) {
    Text(
      text = tag,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun MetaRow(
  room: String,
  duration: String,
  language: String?,
) {
  FlowRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(Res.drawable.ic_location_on),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = room,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }

    Text(
      text = "\u2022",
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.padding(horizontal = 2.dp),
    )

    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(Res.drawable.ic_schedule),
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = duration,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
    val emoji = EmojiUtils.getLanguageInEmoji(language)
    if (emoji != null) {
      Text(
        text = "\u2022",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 2.dp),
      )
      Text(
        text = emoji,
        style = MaterialTheme.typography.bodySmall,
      )
    }
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

// region Previews

@Preview
@Composable
private fun SessionRowDefaultPreview() {
  SessionRow(
    uiSession = UISession(
      id = "1",
      title = "Building Reactive UIs with Compose and State",
      language = "french",
      speakers = listOf(UISession.Speaker("John Doe")),
      roomId = "1",
      room = "Moebius",
      startDate = Instant.parse("2022-04-25T09:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T09:40:00+02:00"),
      isServiceSession = false,
      isFavorite = false,
      tags = listOf("Kotlin", "Compose"),
    ),
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
}

@Preview
@Composable
private fun SessionRowBookmarkedPreview() {
  SessionRow(
    uiSession = UISession(
      id = "2",
      title = "Advanced Kotlin Coroutines in Production",
      language = "english",
      speakers = listOf(UISession.Speaker("John Doe"), UISession.Speaker("Jane Smith")),
      roomId = "2",
      room = "Blin",
      startDate = Instant.parse("2022-04-25T10:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T10:40:00+02:00"),
      isServiceSession = false,
      isFavorite = true,
      tags = listOf("Kotlin", "Coroutines", "Architecture"),
    ),
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
}

@Preview
@Composable
private fun SessionRowNoTagsPreview() {
  SessionRow(
    uiSession = UISession(
      id = "3",
      title = "Fireside Chat: The Future of Android",
      language = null,
      speakers = listOf(UISession.Speaker("Alice Martin")),
      roomId = "1",
      room = "Moebius",
      startDate = Instant.parse("2022-04-25T14:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T14:45:00+02:00"),
      isServiceSession = false,
      isFavorite = false,
    ),
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
}

@Preview
@Composable
private fun SessionRowAppClinicPreview() {
  SessionRow(
    uiSession = UISession(
      id = "4",
      title = "App Clinic: Get Your App Reviewed",
      language = "english",
      speakers = emptyList(),
      roomId = "3",
      room = "242",
      startDate = Instant.parse("2022-04-25T11:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T11:40:00+02:00"),
      isServiceSession = false,
      isFavorite = false,
      isAppClinic = true,
    ),
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
}

@Preview
@Composable
private fun SessionRowManySpeakersPreview() {
  SessionRow(
    uiSession = UISession(
      id = "5",
      title = "Panel: Multiplatform in the Real World",
      language = "english",
      speakers = listOf(
        UISession.Speaker("John Doe"),
        UISession.Speaker("Jane Smith"),
        UISession.Speaker("Alice Martin"),
      ),
      roomId = "1",
      room = "Moebius",
      startDate = Instant.parse("2022-04-25T15:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T15:40:00+02:00"),
      isServiceSession = false,
      isFavorite = true,
      tags = listOf("KMP"),
    ),
    onSessionClick = {},
    onSessionBookmark = { _, _ -> },
    onApplyForAppClinicClick = {}
  )
}

@Preview
@Composable
private fun ServiceSessionRowPreview() {
  ServiceSessionRow(
    session = UISession(
      id = "6",
      title = "Lunch Break",
      language = null,
      speakers = emptyList(),
      roomId = "",
      room = "",
      startDate = Instant.parse("2022-04-25T12:00:00+02:00"),
      endDate = Instant.parse("2022-04-25T13:00:00+02:00"),
      isServiceSession = true,
      isFavorite = false,
    ),
  )
}

// endregion

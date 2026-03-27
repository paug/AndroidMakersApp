package com.androidmakers.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import kotlin.time.Clock
import kotlin.time.Duration

@Composable
fun MessageCard(
  message: FeedItem.Message,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth().neoBrutalElevation(),
    shape = MaterialTheme.shapes.large,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      CategoryTimeRow(
        category = message.type.label(),
        timeAgo = message.createdAt.toRelativeTime(),
      )
      Text(
        text = message.title,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 8.dp),
      )
      Text(
        text = message.body,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp),
      )
    }
  }
}

private fun MessageType.label(): String = when (this) {
  MessageType.INFO -> "INFO"
  MessageType.ALERT -> "ALERT"
  MessageType.ANNOUNCEMENT -> "ANNOUNCEMENT"
}

private fun kotlinx.datetime.Instant.toRelativeTime(): String {
  val now = Clock.System.now()
  val duration: Duration = now - this
  val durationMinutes = duration.inWholeMinutes
  return when {
    durationMinutes < 1 -> "just now"
    durationMinutes < 60 -> "${durationMinutes}m ago"
    durationMinutes < 1440 -> "${durationMinutes / 60}h ago"
    else -> "${durationMinutes / 1440}d ago"
  }
}

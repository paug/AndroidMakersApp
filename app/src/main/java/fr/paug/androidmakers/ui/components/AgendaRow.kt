package fr.paug.androidmakers.ui.components

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.TimeUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.time.OffsetDateTime

@Composable
fun AgendaRow(
    uiSession: UISession
) {
  Column(
      modifier = Modifier.padding(12.dp)
  ) {
    Text(
        text = uiSession.title,
        style = MaterialTheme.typography.h6
    )
    Row {
      Column {
        Text(
            text = uiSession.subtitle(LocalContext.current),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 4.dp)
        )

        val speakers = uiSession.speakers
            .map { it.name }
            .joinToString(", ")
        if (speakers.isNotBlank()) {
          Text(
              text = speakers,
              style = MaterialTheme.typography.subtitle1,
              //modifier = Modifier.padding(top = 4.dp)
          )
        }
      }
      Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        val isBookmarked = SessionSelector.selected(uiSession.id).collectAsState(false)
        val tint by animateColorAsState(
            if (isBookmarked.value) Color(0xffffdd1c)
            else Color.LightGray
        )

        IconToggleButton(
            checked = isBookmarked.value,
            onCheckedChange = {
              SessionSelector.setSessionSelected(uiSession.id, it)
            },
        ) {
          Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "favorite",
              tint = tint
          )
        }
      }
    }
  }
}

private fun UISession.subtitle(context: Context) = buildString {
  val duration = TimeUtils.formatDuration(
      context = context,
      endDate.toEpochMilli() - startDate.toEpochMilli()
  )
  append(duration)
  if (room != null) {
    append(" / ${room}")
  }
  val emoji = EmojiUtils.getLanguageInEmoji(language)
  if (emoji != null) {
    append(" / $emoji")
  }
}

@Preview
@Composable
fun AgendaRowPreview() {
  AndroidMakersTheme {
    AgendaRow(fakeUiSession)
  }
}

private val fakeUiSession = UISession(
    id = "1",
    title = "Why did the chicken cross the road?",
    language = "french",
    speakers = listOf(UISession.Speaker("chicken1")),
    room = "Moebius",
    startDate = OffsetDateTime.parse("2022-04-25T09:00:00+02:00").toInstant(),
    endDate = OffsetDateTime.parse("2022-04-25T10:00:00+02:00").toInstant(),
)
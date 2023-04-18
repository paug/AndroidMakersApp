package fr.paug.androidmakers.ui.components.agenda

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AMColor
import fr.paug.androidmakers.util.BookmarksStore
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.TimeUtils
import kotlinx.datetime.Instant


@Composable
fun AgendaRow(
    uiSession: UISession,
    modifier: Modifier = Modifier
) {
  ListItem(
      modifier = modifier,
//      leadingContent = {
//        Divider(
//            modifier = Modifier
//                .fillMaxHeight()
//                .width(4.dp),
//            thickness = 80.dp,
//            color = MaterialTheme.colorScheme.primary
//        )
//      },
      headlineContent = {
        Text(
            text = uiSession.title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
      },
      supportingContent = {
        Column {
          val speakers = uiSession.speakers.joinToString(", ") { it.name }
          if (speakers.isNotBlank()) {
            Text(
                text = speakers,
                style = MaterialTheme.typography.labelMedium,
            )
          }

          Text(
              text = uiSession.subtitle(LocalContext.current),
              style = MaterialTheme.typography.labelMedium,
              modifier = Modifier.padding(top = 4.dp)
          )
        }
      },
      trailingContent = {
        Box {
          val isBookmarked =
              BookmarksStore.subscribe(uiSession.id).collectAsState(false)
          val imageVector = if (isBookmarked.value) Icons.Rounded.BookmarkRemove
          else Icons.Rounded.BookmarkAdd

          val tint by animateColorAsState(
              if (isBookmarked.value) AMColor.bookmarked
              else Color.LightGray
          )

          IconToggleButton(
              checked = isBookmarked.value,
              onCheckedChange = {
                BookmarksStore.setBookmarked(uiSession.id, it)
              },
          ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "favorite",
                tint = tint
            )
          }
        }
      },
      overlineContent = {
        // Nothing to do
      },
  )
}

private fun UISession.subtitle(context: Context) = buildString {
  val millis = endDate - startDate
  val duration = TimeUtils.formatDuration(
      context = context,
      millis
  )

  append(duration)
  append(" / $room")
  val emoji = EmojiUtils.getLanguageInEmoji(language)
  if (emoji != null) {
    append(" / $emoji")
  }
}

@Preview
@Composable
private fun AgendaRowPreview() {
  AgendaRow(fakeUiSession)
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
)

@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TitleCard
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ResponsiveListHeader
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.common.Loading
import fr.paug.androidmakers.wear.ui.common.PulsatingRedDot
import fr.paug.androidmakers.wear.ui.session.UISession
import fr.paug.androidmakers.wear.ui.session.uiSession1
import fr.paug.androidmakers.wear.ui.session.uiSession2
import fr.paug.androidmakers.wear.ui.theme.amChipBackground
import fr.paug.androidmakers.wear.ui.theme.amChipText
import fr.paug.androidmakers.wear.ui.theme.amPurple
import fr.paug.androidmakers.wear.ui.theme.amPurpleLight
import fr.paug.androidmakers.wear.ui.theme.amRed
import fr.paug.androidmakers.wear.ui.theme.amRedDark
import fr.paug.androidmakers.wear.ui.theme.amRedLight
import fr.paug.androidmakers.wear.ui.theme.amSeparator

@Composable
fun SessionDetailScreen(viewModel: SessionDetailViewModel) {
  val session by viewModel.uiSession.collectAsStateWithLifecycle()
  val currentSession = session
  if (currentSession == null) {
    Loading()
  } else {
    Session(
      session = currentSession,
      onBookmarkToggle = { viewModel.bookmark(it) }
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Session(session: UISession, onBookmarkToggle: (Boolean) -> Unit) {
  val columnState = rememberResponsiveColumnState(
    contentPadding = ScalingLazyColumnDefaults.padding(
      first = ScalingLazyColumnDefaults.ItemType.Text,
      last = ScalingLazyColumnDefaults.ItemType.Chip,
    )
  )
  ScreenScaffold(scrollState = columnState) {
    ScalingLazyColumn(
      columnState = columnState,
      modifier = Modifier.fillMaxSize()
    ) {
      // 1. Metadata pills row — Duration + Language + Ongoing dot
      item {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Text(
              text = session.formattedDuration,
              style = MaterialTheme.typography.caption1,
              color = amPurple,
              modifier = Modifier
                .background(
                  color = amPurpleLight,
                  shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp),
            )

            if (session.isOngoing) {
              PulsatingRedDot()
            }
          }

          languageWithFlag(session.session.language)?.let { langText ->
            Text(
              text = langText,
              style = MaterialTheme.typography.caption1,
              color = amRedDark,
              modifier = Modifier
                .background(
                  color = amRedLight,
                  shape = RoundedCornerShape(50)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp),
            )
          }
        }
      }

      // 2. Title
      item {
        Text(
          modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
          text = session.session.title,
          textAlign = TextAlign.Start,
          style = MaterialTheme.typography.title2.copy(
            fontWeight = FontWeight.Bold,
          ),
        )
      }

      // 3. Time + Room row with icons
      item {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Start,
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        ) {
          Icon(
            imageVector = Icons.Rounded.Schedule,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colors.onSurfaceVariant,
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = session.session.startsAt.time.toString(),
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.onSurfaceVariant,
          )
          Text(
            text = " \u2022 ",
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.onSurfaceVariant,
          )
          Icon(
            imageVector = Icons.Rounded.Place,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colors.onSurfaceVariant,
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = session.room.name,
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.onSurfaceVariant,
          )
        }
      }

      // 4. Tags chips row (conditional)
      val tags = session.session.tags
      val complexity = session.session.complexity
      if (tags.isNotEmpty() || complexity != null) {
        item {
          FlowRow(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            tags.forEach { tag ->
              Text(
                text = tag,
                style = MaterialTheme.typography.caption2,
                color = amChipText,
                modifier = Modifier
                  .background(
                    color = amChipBackground,
                    shape = RoundedCornerShape(8.dp)
                  )
                  .padding(horizontal = 8.dp, vertical = 4.dp),
              )
            }
            complexity?.let {
              Text(
                text = it.name.lowercase().replaceFirstChar { c -> c.uppercase() },
                style = MaterialTheme.typography.caption2,
                color = amChipText,
                modifier = Modifier
                  .background(
                    color = amChipBackground,
                    shape = RoundedCornerShape(8.dp)
                  )
                  .padding(horizontal = 8.dp, vertical = 4.dp),
              )
            }
          }
        }
      }

      // 5. Separator
      session.session.description?.let { description ->
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
              .height(1.dp)
              .background(amSeparator)
          )
        }

        // 6. "About this talk" header
        item {
          ResponsiveListHeader {
            Text(text = stringResource(R.string.session_detail_about))
          }
        }

        // 7. Description body
        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 8.dp),
            text = description,
            style = MaterialTheme.typography.body2.copy(
              hyphens = Hyphens.Auto,
              lineBreak = LineBreak.Paragraph,
            ),
            color = MaterialTheme.colors.onSurfaceVariant,
          )
        }
      }

      // 8. Separator + Speakers section
      if (session.speakers.isNotEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
              .height(1.dp)
              .background(amSeparator)
          )
        }

        // 9. "Speakers" header
        item {
          ResponsiveListHeader {
            Text(text = stringResource(R.string.session_detail_speakers))
          }
        }

        // 10. Speaker cards
        for (speaker in session.speakers) {
          item {
            TitleCard(
              modifier = Modifier.fillMaxWidth(),
              backgroundPainter = ColorPainter(amPurpleLight.copy(alpha = 0.15f)),
              title = {
                Text(
                  text = speaker.name ?: "",
                  fontWeight = FontWeight.Bold,
                )
              },
              onClick = {},
            ) {
              Column {
                if (!speaker.company.isNullOrBlank()) {
                  Text(
                    text = speaker.company!!,
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onSurfaceVariant,
                  )
                }
                speaker.bio?.let { bio ->
                  Text(
                    text = bio,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                  )
                }
              }
            }
          }
        }
      }

      // 11. Bookmark action Chip
      item {
        Chip(
          modifier = Modifier.fillMaxWidth(),
          label = {
            Text(
              text = stringResource(
                if (session.isBookmarked) R.string.session_detail_removeBookmark
                else R.string.session_detail_addBookmark
              ),
            )
          },
          icon = {
            Icon(
              imageVector = if (session.isBookmarked) Icons.Rounded.BookmarkRemove
              else Icons.Rounded.BookmarkAdd,
              contentDescription = null,
              modifier = Modifier.size(18.dp),
            )
          },
          colors = ChipDefaults.chipColors(
            backgroundColor = if (session.isBookmarked) amRed.copy(alpha = 0.8f) else amPurple,
            contentColor = Color.White,
            iconColor = Color.White,
          ),
          onClick = { onBookmarkToggle(!session.isBookmarked) },
        )
      }
    }
  }
}

private fun languageWithFlag(language: String): String? {
  if (language.isBlank()) return null
  val flag = when (language.lowercase()) {
    "english" -> "\uD83C\uDDEC\uD83C\uDDE7"
    "french", "français" -> "\uD83C\uDDEB\uD83C\uDDF7"
    else -> null
  }
  return if (flag != null) "$language $flag" else language
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun SessionDetailBookmarkedPreview() {
  Session(
    session = uiSession1,
    onBookmarkToggle = {}
  )
}

@WearPreviewDevices
@Composable
private fun SessionDetailNotBookmarkedPreview() {
  Session(
    session = uiSession2,
    onBookmarkToggle = {}
  )
}

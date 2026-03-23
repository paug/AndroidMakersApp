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
import androidx.compose.material.icons.rounded.BookmarkAdded
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
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
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
import fr.androidmakers.domain.model.Complexity
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.common.Loading
import fr.paug.androidmakers.wear.ui.common.PulsatingRedDot
import fr.paug.androidmakers.wear.ui.session.UISession
import fr.paug.androidmakers.wear.ui.session.uiSession1
import fr.paug.androidmakers.wear.ui.session.uiSession2
import fr.paug.androidmakers.wear.ui.theme.amChipBackground
import fr.paug.androidmakers.wear.ui.theme.amChipText
import fr.paug.androidmakers.wear.ui.theme.amGreen
import fr.paug.androidmakers.wear.ui.theme.amGreenLight
import fr.paug.androidmakers.wear.ui.theme.amRed
import fr.paug.androidmakers.wear.ui.theme.amRedDark
import fr.paug.androidmakers.wear.ui.theme.amRedLight
import fr.paug.androidmakers.wear.ui.theme.amSeparator
import kotlinx.datetime.LocalDateTime

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
      item {
        SessionMetadataPills(
          formattedDuration = session.formattedDuration,
          isOngoing = session.isOngoing,
          language = session.session.language,
        )
      }

      item {
        Text(
          modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
          text = session.session.title,
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.title2.copy(
            fontWeight = FontWeight.Bold,
          ),
        )
      }

      item {
        SessionTimeRoomRow(
          startsAt = session.session.startsAt,
          roomName = session.room.name,
        )
      }

      val tags = session.session.tags
      val complexity = session.session.complexity
      if (tags.isNotEmpty() || complexity != null) {
        item {
          SessionTagsFlowRow(tags = tags, complexity = complexity)
        }
      }

      session.session.description?.let { description ->
        sessionDescriptionSection(description)
      }

      if (session.speakers.isNotEmpty()) {
        sessionSpeakersSection(session.speakers)
      }

      item {
        SessionBookmarkChip(
          isBookmarked = session.isBookmarked,
          onBookmarkToggle = onBookmarkToggle,
        )
      }
    }
  }
}

@Composable
private fun SessionMetadataPills(
  formattedDuration: String,
  isOngoing: Boolean,
  language: String,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        modifier = Modifier
          .background(
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(50)
          )
          .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        if (isOngoing) {
          PulsatingRedDot()
          Spacer(modifier = Modifier.width(4.dp))
        }

        Text(
          text = formattedDuration,
          style = MaterialTheme.typography.caption1,
          color = MaterialTheme.colors.onPrimary,
        )
      }
    }

    languageWithFlag(language)?.let { langText ->
      Spacer(modifier = Modifier.width(8.dp))
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

@Composable
private fun SessionTimeRoomRow(
  startsAt: LocalDateTime,
  roomName: String,
) {
  FlowRow(
    horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally),
    verticalArrangement = Arrangement.spacedBy(6.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 4.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Icon(
        imageVector = Icons.Rounded.Schedule,
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colors.onSurfaceVariant,
      )
      Text(
        text = startsAt.time.toString(),
        style = MaterialTheme.typography.caption1,
        color = MaterialTheme.colors.onSurfaceVariant,
      )
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Icon(
        imageVector = Icons.Rounded.Place,
        contentDescription = null,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colors.onSurfaceVariant,
      )
      Text(
        text = roomName,
        style = MaterialTheme.typography.caption1,
        color = MaterialTheme.colors.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SessionTagsFlowRow(
  tags: List<String>,
  complexity: Complexity?,
) {
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

private fun ScalingLazyListScope.sessionDescriptionSection(description: String) {
  item {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .height(1.dp)
        .background(amSeparator)
    )
  }

  item {
    ResponsiveListHeader {
      Text(text = stringResource(R.string.session_detail_about))
    }
  }

  item {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
      text = description,
      style = MaterialTheme.typography.body1.copy(
        hyphens = Hyphens.Auto,
        lineBreak = LineBreak.Paragraph,
      ),
      color = MaterialTheme.colors.onSurfaceVariant,
    )
  }
}

private fun ScalingLazyListScope.sessionSpeakersSection(
  speakers: List<Speaker>,
) {
  item {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .height(1.dp)
        .background(amSeparator)
    )
  }

  item {
    ResponsiveListHeader {
      Text(text = stringResource(R.string.session_detail_speakers))
    }
  }

  for (speaker in speakers) {
    item {
      TitleCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundPainter = ColorPainter(amGreenLight.copy(alpha = 0.15f)),
        title = {
          Text(
            text = speaker.name ?: "",
            fontWeight = FontWeight.Bold,
          )
        },
        onClick = {},
        enabled = false,
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
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SessionBookmarkChip(
  isBookmarked: Boolean,
  onBookmarkToggle: (Boolean) -> Unit,
) {
  Chip(
    modifier = Modifier.fillMaxWidth(),
    label = {
      Text(
        text = stringResource(
          if (isBookmarked) R.string.session_detail_removeBookmark
          else R.string.session_detail_addBookmark
        ),
      )
    },
    icon = {
      Icon(
        imageVector = if (isBookmarked) Icons.Rounded.BookmarkAdded
        else Icons.Rounded.BookmarkAdd,
        contentDescription = null,
        modifier = Modifier.size(18.dp),
      )
    },
    colors = ChipDefaults.chipColors(
      backgroundColor = if (isBookmarked) amRed.copy(alpha = 0.8f) else amGreen,
      contentColor = Color.White,
      iconColor = Color.White,
    ),
    onClick = { onBookmarkToggle(!isBookmarked) },
  )
}

private fun languageWithFlag(language: String): String? {
  if (language.isBlank()) return null
  val flag = when (language.lowercase()) {
    "english" -> "\uD83C\uDDEC\uD83C\uDDE7"
    "french", "français" -> "\uD83C\uDDEB\uD83C\uDDF7"
    else -> null
  }
  return if (flag != null) flag else language
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

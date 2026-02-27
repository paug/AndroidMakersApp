@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.LocalTextStyle
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import fr.paug.androidmakers.wear.ui.common.Loading
import fr.paug.androidmakers.wear.ui.common.PulsatingRedDot
import fr.paug.androidmakers.wear.ui.session.UISession
import fr.paug.androidmakers.wear.ui.session.uiSession1
import fr.paug.androidmakers.wear.ui.theme.amPurple
import fr.paug.androidmakers.wear.ui.theme.amPurpleLight
import fr.paug.androidmakers.wear.ui.theme.amRed

@Composable
fun SessionDetailScreen(viewModel: SessionDetailViewModel) {
  val session by viewModel.uiSession.collectAsStateWithLifecycle()
  val currentSession = session
  if (currentSession == null) {
    Loading()
  } else {
    Session(currentSession)
  }
}

@Composable
private fun Session(session: UISession) {
  val columnState = rememberResponsiveColumnState(
    contentPadding = ScalingLazyColumnDefaults.padding(
      first = ScalingLazyColumnDefaults.ItemType.Text,
      last = ScalingLazyColumnDefaults.ItemType.Text,
    )
  )
  ScreenScaffold(scrollState = columnState) {
    ScalingLazyColumn(
      columnState = columnState,
      modifier = Modifier.fillMaxSize()
    ) {
      // Duration chip + bookmark indicator
      item {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxWidth(),
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

          if (session.isBookmarked) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              modifier = Modifier.size(16.dp),
              imageVector = Icons.Rounded.Bookmark,
              tint = amRed,
              contentDescription = "Bookmarked"
            )
          }

          if (session.isOngoing) {
            Spacer(modifier = Modifier.width(8.dp))
            PulsatingRedDot()
          }
        }
      }

      // Title
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

      // Time and room
      item {
        CompositionLocalProvider(
          LocalContentColor provides MaterialTheme.colors.onSurfaceVariant,
          LocalTextStyle provides MaterialTheme.typography.caption1,
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 4.dp),
          ) {
            Text(
              text = session.session.startsAt.time.toString(),
            )
            Text(
              text = " \u2022 ",
            )
            Text(
              text = session.room.name,
            )
          }
        }
      }

      // Speakers
      if (session.speakers.isNotEmpty()) {
        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
            text = session.speakers.joinToString { it.getFullNameAndCompany() },
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption1,
          )
        }
      }

      // Description
      session.session.description?.let { description ->
        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 12.dp),
            text = "About this talk",
            style = MaterialTheme.typography.caption1.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
            ),
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
          )
        }

        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 8.dp, vertical = 4.dp),
            text = description,
            style = MaterialTheme.typography.body1.copy(
              hyphens = Hyphens.Auto,
              lineBreak = LineBreak.Paragraph,
            ),
          )
        }
      }

      // Speaker bios
      for (speaker in session.speakers) {
        val bio = speaker.bio ?: continue
        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 12.dp),
            text = speaker.getFullNameAndCompany(),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption1.copy(
              fontWeight = FontWeight.Bold,
            ),
          )
        }

        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 8.dp),
            text = bio,
            style = MaterialTheme.typography.body2.copy(
              hyphens = Hyphens.Auto,
              lineBreak = LineBreak.Paragraph,
              color = MaterialTheme.colors.onSurfaceVariant,
            ),
          )
        }
      }
    }
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun SessionDetailsScreenPreview() {
  Session(
    session = uiSession1
  )
}

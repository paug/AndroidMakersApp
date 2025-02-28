@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import fr.paug.androidmakers.wear.ui.theme.amRed

@Composable
fun SessionDetailScreen(viewModel: SessionDetailViewModel) {
  val sessionState: State<UISession?> = viewModel.uiSession.collectAsState(null)
  val session = sessionState.value
  if (session == null) {
    Loading()
  } else {
    Session(session)
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
      item {
        CompositionLocalProvider(
          LocalContentColor provides MaterialTheme.colors.onSurface,
          LocalTextStyle provides MaterialTheme.typography.caption1,
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
          ) {
            if (session.isBookmarked) {
              Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Rounded.Bookmark,
                tint = amRed,
                contentDescription = "Bookmarked"
              )

              Spacer(modifier = Modifier.width(2.dp))
            }

            Text(
              text = session.session.startsAt.time.toString(),
            )

            Spacer(modifier = Modifier.width(16.dp))

            if (session.isOngoing) {
              PulsatingRedDot()
              Spacer(modifier = Modifier.width(2.dp))
            }
            Text(
              text = session.formattedDuration,
            )
          }
        }
      }

      item {
        Text(
          modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 12.dp),
          text = session.session.title,
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.title2,
        )
      }

      if (session.speakers.isNotEmpty()) {
        item {
          Text(
            modifier = Modifier
              .padding(horizontal = 16.dp)
              .padding(top = 16.dp),
            text = session.speakers.joinToString { it.getFullNameAndCompany() },
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
          )
        }
      }

      item {
        Text(
          text = session.room.name,
          color = MaterialTheme.colors.secondary,
          textAlign = TextAlign.Center,
        )
      }

      session.session.description?.let { description ->
        item {
          Text(
            modifier = Modifier
              .fillMaxWidth()
              .padding(start = 8.dp, top = 16.dp, end = 8.dp),
            text = description,
            style = MaterialTheme.typography.body1.copy(
              hyphens = Hyphens.Auto,
              lineBreak = LineBreak.Paragraph,
            ),
          )
        }
      }

      for (speaker in session.speakers) {
        val bio = speaker.bio ?: continue
        item {
          Text(
            modifier = Modifier
              .padding(horizontal = 16.dp)
              .padding(top = 16.dp),
            text = speaker.getFullNameAndCompany(),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
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
              color = MaterialTheme.colors.onSurface,
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

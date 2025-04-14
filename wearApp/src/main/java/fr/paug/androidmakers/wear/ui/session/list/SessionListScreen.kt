@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.CardDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.LocalTextStyle
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
import com.google.android.horologist.compose.material.ListHeaderDefaults
import com.google.android.horologist.compose.material.ResponsiveListHeader
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.common.Loading
import fr.paug.androidmakers.wear.ui.common.PulsatingRedDot
import fr.paug.androidmakers.wear.ui.common.SaveableLaunchedEffect
import fr.paug.androidmakers.wear.ui.session.UISession
import fr.paug.androidmakers.wear.ui.session.uiSessions
import fr.paug.androidmakers.wear.ui.theme.amRed

@Composable
fun SessionListScreen(
  sessions: List<UISession>?,
  title: String,
  isResumed: Boolean,
  onSessionClick: (String) -> Unit,
) {
  if (sessions == null) {
    Loading()
  } else {
    SessionList(
      sessions = sessions,
      title = title,
      isResumed = isResumed,
      onSessionClick = onSessionClick,
    )
  }
}

@Composable
private fun SessionList(
  sessions: List<UISession>,
  title: String,
  isResumed: Boolean,
  onSessionClick: (String) -> Unit,
) {
  val columnState = rememberResponsiveColumnState(
    contentPadding = ScalingLazyColumnDefaults.padding(
      first = ScalingLazyColumnDefaults.ItemType.Card,
      last = ScalingLazyColumnDefaults.ItemType.Card,
    )
  )

  val density = LocalDensity.current
  SaveableLaunchedEffect(isResumed) {
    val nextSessionIndex = sessions.nextSessionIndex()
    if (nextSessionIndex > 0) {
      // Approximation of about half the height of a card, so the card is centered when scrolling to it.
      // Maybe there's a way to get the actual height?
      val scrollOffset = with(density) { 80.dp.roundToPx() }
      columnState.state.scrollToItem(
        // Add 1 to the index to account for the title
        index = nextSessionIndex + 1,
        scrollOffset = scrollOffset,
      )
    }
  }

  ScreenScaffold(scrollState = columnState) {
    ScalingLazyColumn(
      columnState = columnState,
      modifier = Modifier.fillMaxSize()
    ) {
      item {
        ResponsiveListHeader(contentPadding = ListHeaderDefaults.firstItemPadding()) {
          Text(text = title)
        }
      }
      if (sessions.isEmpty()) {
        item {
          Text(
            text = stringResource(id = R.string.session_list_noSessions),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption2,
          )
        }
      } else {
        items(sessions, key = { it.session.id }) { session ->
          SessionItem(
            session = session,
            onSessionClick = onSessionClick,
          )
        }
      }
    }
  }
}

private fun List<UISession>.nextSessionIndex(): Int {
  return indexOfFirst { !it.isOver }
}

@Composable
private fun SessionItem(
  session: UISession,
  onSessionClick: (String) -> Unit,
) {
  TitleCard(
    modifier = Modifier
      .fillMaxWidth()
      .let {
        if (session.isOver) {
          it.alpha(.7F)
        } else {
          it
        }
      },
    backgroundPainter = if (session.session.isServiceSession) {
      ColorPainter(MaterialTheme.colors.surface.copy(alpha = .6F))
    } else {
      CardDefaults.cardBackgroundPainter()
    },
    title = {
      Column(
        modifier = Modifier.fillMaxWidth(),
      ) {
        CompositionLocalProvider(
          LocalContentColor provides MaterialTheme.colors.onSurfaceVariant,
          LocalTextStyle provides MaterialTheme.typography.caption1,
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
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
              modifier = Modifier.weight(1F),
              text = session.session.startsAt.time.toString(),
            )

            if (session.isOngoing) {
              PulsatingRedDot()
              Spacer(modifier = Modifier.width(2.dp))
            }
            Text(
              text = session.formattedDuration,
            )
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = session.session.title,
          style = MaterialTheme.typography.title3.copy(
            hyphens = Hyphens.Auto,
            lineBreak = LineBreak.Paragraph,
          ),
        )
      }
    },
    onClick = { onSessionClick(session.session.id) },
    enabled = !session.session.isServiceSession,
  ) {
    if (!session.session.isServiceSession) {
      if (session.speakers.isNotEmpty()) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = session.speakers.joinToString { it.getFullNameAndCompany() },
          color = MaterialTheme.colors.primary,
        )
        Spacer(modifier = Modifier.height(4.dp))
      }
      Text(
        text = session.room.name,
        color = MaterialTheme.colors.secondary,
      )
    }
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun LoadingSessionListScreenPreview() {
  SessionListScreen(null, stringResource(id = R.string.main_day1), true, {})
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun LoadedSessionListScreenPreview() {
  SessionListScreen(uiSessions, stringResource(id = R.string.main_day1), true, {})
}

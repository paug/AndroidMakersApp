@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.CircularProgressIndicator
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
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.ListHeaderDefaults
import com.google.android.horologist.compose.material.ResponsiveListHeader
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.ui.main.UISession
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

@Composable
fun SessionListScreen(
    sessions: List<UISession>?,
    title: String,
) {
  if (sessions == null) {
    Loading()
  } else {
    SessionList(
        sessions = sessions,
        title = title,
    )
  }
}

@Composable
private fun SessionList(
    sessions: List<UISession>,
    title: String,
) {
  val columnState = rememberResponsiveColumnState()
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
        SessionItem(session)
      }
    }
  }
}

@Composable
private fun SessionItem(session: UISession) {
  TitleCard(
      modifier = Modifier.fillMaxWidth(),
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
                    tint = MaterialTheme.colors.secondaryVariant,
                    contentDescription = "Bookmarked"
                )
              }

              Text(
                  modifier = Modifier.weight(1F),
                  text = session.session.startsAt.time.toString(),
              )

              Text(
                  text = session.formattedDuration,
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = session.session.title)
        }
      },
      onClick = { /*TODO*/ },
  ) {
    if (!session.session.isServiceSession) {
      if (session.speakers.isNotEmpty()) {
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = session.speakers.joinToString { it.getFullNameAndCompany() },
            color = MaterialTheme.colors.primaryVariant,
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

@Composable
private fun Loading() {
  Box(
      modifier = Modifier
          .fillMaxSize(),
      contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun LoadingSessionListScreenPreview() {
  SessionListScreen(null, stringResource(id = R.string.main_day1))
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
private fun LoadedSessionListScreenPreview() {
  SessionListScreen(
      listOf(
          UISession(
              session = Session(
                  id = "1",
                  title = "Android Graphics: the Path to [UI] Riches",
                  description = "Android's graphics APIs are extensive and powerful... but maybe a little complicated. This session will show ways to use the graphics APIs to achieve cool effects and improve the visual quality and richness of your applications.",
                  roomId = "",
                  speakers = emptyList(),
                  startsAt = LocalDateTime(2023, Month.APRIL, 27, 9, 15),
                  endsAt = LocalDateTime(2023, Month.APRIL, 27, 10, 0),
                  isServiceSession = false,
              ),
              speakers = listOf(
                  Speaker(
                      id = "1",
                      name = "Speaker 1",
                      bio = "Bio 1",
                  ),
                  Speaker(
                      id = "2",
                      name = "Speaker 2",
                      bio = "Bio 2",
                  )
              ),
              room = Room(
                  id = "1",
                  name = "Room 1"
              ),
              isBookmarked = true,
          ),
          UISession(
              session = Session(
                  id = "2",
                  title = "Using Compose Runtime to create a client library",
                  description = "Jetpack Compose (UI) is a powerful UI toolkit for Android. Have you ever wondered where this power comes from? The answer is Compose Runtime. \r\n\r\nIn this talk, we will see how we can use Compose Runtime to create client libraries. Firstly, we will talk about Compose nodes, Composition, Recomposer, and how they are orchestrated to create a slot table. Then, we will see how the changes in the slot table are applied with an Applier. Moreover, we will touch upon the Snapshot system and how the changes in the state objects trigger a recomposition. Finally, we will create a basic UI toolkit for PowerPoint using Compose Runtime.",
                  roomId = "",
                  speakers = emptyList(),
                  startsAt = LocalDateTime(2023, Month.APRIL, 27, 10, 15),
                  endsAt = LocalDateTime(2023, Month.APRIL, 27, 11, 0),
                  isServiceSession = false,
              ),
              speakers = listOf(
                  Speaker(
                      id = "3",
                      name = "Speaker 3",
                      bio = "Bio 3",
                  ),
              ),
              room = Room(
                  id = "2",
                  name = "Room 2"
              ),
              isBookmarked = false,
          ),
      ),
      stringResource(id = R.string.main_day1)
  )
}

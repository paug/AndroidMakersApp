package fr.paug.androidmakers.ui.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.text.format.DateUtils
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.theme.AMColor
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import java.util.*

sealed class SessionDetailState {
  object Loading : SessionDetailState()
  data class Loaded(
      val session: Session,
      val room: Room,
      val startTimestamp: Long,
      val endTimestamp: Long,
      val isBookmarked: Boolean,
  ) : SessionDetailState()
}

@Composable
fun SessionDetailLayout(
    sessionDetailState: SessionDetailState,
    onBackClick: () -> Unit,
    onBookmarkClick: (bookmarked: Boolean) -> Unit,
) {
  val formattedDateAndRoom: String? = if (sessionDetailState is SessionDetailState.Loaded) {
    getFormattedDateAndRoom(
        room = sessionDetailState.room,
        startTimestamp = sessionDetailState.startTimestamp,
        endTimestamp = sessionDetailState.endTimestamp,
    )
  } else {
    null
  }

  AndroidMakersTheme {
    Scaffold(
        topBar = {
          TopAppBar(
              navigationIcon = {
                IconButton(onClick = onBackClick) {
                  Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
              },
              title = {
                if (sessionDetailState is SessionDetailState.Loaded) {
                  Text(sessionDetailState.session.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
              },
              actions = {
                if (sessionDetailState is SessionDetailState.Loaded) {
                  val context = LocalContext.current
                  IconButton(
                      onClick = {
                        // TODO Ideally this should not be handled here but by the caller
                        shareSession(
                            context = context,
                            session = sessionDetailState.session,
                            sessionDateAndRoom = formattedDateAndRoom!!,
                        )
                      }
                  ) {
                    Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                  }
                }
              }
          )
        },
        floatingActionButton = {
          if (sessionDetailState is SessionDetailState.Loaded) {
            val backgroundColor by animateColorAsState(
                if (sessionDetailState.isBookmarked) Color.White else AMColor.secondary
            )
            FloatingActionButton(
                backgroundColor = backgroundColor,
                onClick = {
                  onBookmarkClick(!sessionDetailState.isBookmarked)
                }
            ) {
              Image(
                  // TODO animate icon
                  painterResource(
                      if (sessionDetailState.isBookmarked) R.drawable.ic_bookmarked_fab else R.drawable.ic_bookmark_fab
                  ),
                  contentDescription = stringResource(R.string.bookmarked)
              )
            }
          }
        }
    ) { innerPadding ->
      Box(Modifier.padding(innerPadding)) {
        when (sessionDetailState) {
          is SessionDetailState.Loading -> LoadingLayout()
          is SessionDetailState.Loaded -> SessionDetails(sessionDetailState, formattedDateAndRoom!!)

        }
      }
    }
  }
}

@Composable
private fun SessionDetails(sessionDetails: SessionDetailState.Loaded, formattedDateAndRoom: String) {
  Column(Modifier
      .verticalScroll(state = rememberScrollState())
      .padding(16.dp)
  ) {
    Text(text = sessionDetails.session.title, style = MaterialTheme.typography.h5)
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = formattedDateAndRoom,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = sessionDetails.session.description,
        style = MaterialTheme.typography.body1
    )
    FlowRow(
        modifier = Modifier.padding(top = 16.dp),
        mainAxisSpacing = 8.dp,
    ) {
      sessionDetails.session.language.asLanguageResource()?.let { language ->
        Chip(language)
      }
      for (tag in sessionDetails.session.tags) {
        Chip(tag)
      }
      if (sessionDetails.session.complexity.isNotEmpty()) {
        Chip(sessionDetails.session.complexity)
      }
    }
    // To account for the FAB
    Spacer(modifier = Modifier.height(64.dp))
  }
}

@Composable
private fun String.asLanguageResource(): String? {
  return when (this) {
    "English" -> stringResource(R.string.english)
    "French" -> stringResource(R.string.french)
    else -> null
  }
}


@Composable
private fun Chip(text: String) {
  Text(
      modifier = Modifier
          .background(
              shape = RoundedCornerShape(corner = CornerSize(16.dp)),
              color = colorResource(R.color.light_grey)
          )
          .padding(8.dp),
      text = text
  )
}

@Composable
private fun getFormattedDateAndRoom(room: Room, startTimestamp: Long, endTimestamp: Long): String {
  val context = LocalContext.current
  val sessionDate = DateUtils.formatDateRange(context,
      Formatter(context.resources.configuration.locale),
      startTimestamp,
      endTimestamp,
      DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR,
      null).toString()
  return if (room.name.isNotEmpty()) {
    stringResource(R.string.sessionDateWithRoomPlaceholder, sessionDate, room.name)
  } else {
    sessionDate
  }
}

private fun shareSession(context: Context, session: Session, sessionDateAndRoom: String) {
  val speakersList = listOf<Speaker>() // TODO Retrieve speakers
  val speakers = TextUtils.join(", ", speakersList)

  val shareSessionIntent = Intent(Intent.ACTION_SEND)
  shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session.title)
  if (speakersList.isEmpty()) {
    shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
        String.format("%s: %s (%s)", context.getString(R.string.app_name), session.title, sessionDateAndRoom))
  } else {
    shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
        String.format("%s: %s (%s, %s, %s)", context.getString(R.string.app_name), session.title, speakers, sessionDateAndRoom, session.language))
  }
  shareSessionIntent.type = "text/plain"
  context.startActivity(shareSessionIntent)
}


@Preview
@Composable
private fun SessionDetailLayoutLoadingPreview() {
  SessionDetailLayout(
      sessionDetailState = SessionDetailState.Loading,
      onBackClick = {},
      onBookmarkClick = {},
  )
}

@Preview
@Composable
private fun SessionDetailLayoutLoadedPreview() {
  SessionDetailLayout(
      sessionDetailState = SessionDetailState.Loaded(
          session = Session(
              id = "42",
              complexity = "Intermediate",
              speakers = listOf("John Smith", "Jane Doe"),
              description = "Ever wish you could just use a lambda as an onClickListener? Or change the View visibility by modifying a simple \"isVisible\" boolean? Or how about using a doOnLayout{} method to run a block of code after view layout?\r\n\r\nThese are just a few examples of how Android KTX extensions help solve common problems. They improve the existing Jetpack and Android platform APIs so you can consume them from Kotlin in a concise and idiomatic way.\r\n\r\nThis talk will discuss what KTX extensions are and explore the most useful extensions that you can use in your daily workflow.",
              language = "English",
              title = "The title of this session",
              tags = listOf("Tools", "Fun"),
              videoURL = "",
              slido = null,
              platformUrl = null
          ),
          room = Room(
              id = "13",
              name = "The Big Room"
          ),
          startTimestamp = Date(122, 4, 26, 13, 30).time,
          endTimestamp = Date(122, 4, 26, 14, 15).time,
          isBookmarked = false,
      ),
      onBackClick = {},
      onBookmarkClick = {},
  )
}

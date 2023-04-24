package fr.paug.androidmakers.ui.components.session

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.text.format.DateUtils
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.theme.AMColor
import fr.paug.androidmakers.ui.util.discardHtmlTags
import fr.paug.androidmakers.ui.viewmodel.Lce
import fr.paug.androidmakers.util.EmojiUtils
import io.openfeedback.android.components.SessionFeedbackContainer
import separatorColor
import java.util.Formatter
import java.util.Locale

class SessionDetailState(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val isBookmarked: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailLayout(
    sessionDetailState: Lce<SessionDetailState>,
    onBackClick: () -> Unit,
    onBookmarkClick: (bookmarked: Boolean) -> Unit,
) {
  val formattedDateAndRoom: String? = if (sessionDetailState is Lce.Content) {
    getFormattedDateAndRoom(
        room = sessionDetailState.content.room,
        startTimestamp = sessionDetailState.content.startTimestamp,
        endTimestamp = sessionDetailState.content.endTimestamp,
    )
  } else {
    null
  }

  Scaffold(
      modifier = Modifier.navigationBarsPadding(),
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        TopAppBar(
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
              }
            },
            title = {
              // Nothing to do
            },
            actions = {
              if (sessionDetailState is Lce.Content) {
                val context = LocalContext.current
                IconButton(
                    onClick = {
                      // TODO Ideally this should not be handled here but by the caller
                      shareSession(
                          context = context,
                          session = sessionDetailState.content.session,
                          sessionDateAndRoom = formattedDateAndRoom!!,
                          speakersNameList = sessionDetailState.content.speakers.mapNotNull { it.name },
                      )
                    }
                ) {
                  Icon(
                      Icons.Rounded.Share,
                      contentDescription = stringResource(R.string.share)
                  )
                }
              }
            }
        )
      },
      floatingActionButton = {
        if (sessionDetailState is Lce.Content) {
          val backgroundColor by animateColorAsState(
              if (sessionDetailState.content.isBookmarked) AMColor.amRed else Color.White
          )
          FloatingActionButton(
              containerColor = backgroundColor,
              onClick = {
                onBookmarkClick(!sessionDetailState.content.isBookmarked)
              }
          ) {
            Crossfade(sessionDetailState.content.isBookmarked) { isBookmarked ->
              Image(
                  imageVector = if (isBookmarked) Icons.Rounded.BookmarkRemove else Icons.Rounded.BookmarkAdd,
                  contentDescription = stringResource(R.string.bookmarked)
              )
            }
          }
        }
      }
  ) { innerPadding ->
    Box(Modifier.padding(innerPadding)) {
      when (sessionDetailState) {
        is Lce.Loading, Lce.Error -> LoadingLayout()
        is Lce.Content -> SessionDetails(sessionDetailState.content, formattedDateAndRoom!!)
      }
    }
  }
}

@Composable
private fun SessionDetails(sessionDetails: SessionDetailState, formattedDateAndRoom: String) {

  Column(
      Modifier
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp)
  ) {

    Text(
        text = sessionDetails.session.title,
        style = MaterialTheme.typography.headlineLarge
    )

    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
      Icon(
          modifier = Modifier.size(16.dp),
          imageVector = Icons.Rounded.Info,
          contentDescription = stringResource(R.string.info)
      )

      Text(
          text = formattedDateAndRoom,
          style = MaterialTheme.typography.labelMedium,
      )
    }

    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = sessionDetails.session.description?.discardHtmlTags() ?: "",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
    )

    ChipList(sessionDetails)

    Speakers(sessionDetails.speakers)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )

    if (System.currentTimeMillis() > sessionDetails.startTimestamp) {
      SessionFeedbackContainer(
          openFeedback = AndroidMakersApplication.instance().openFeedback,
          sessionId = sessionDetails.session.id,
          language = Locale.getDefault().language,
      )
    } else {
      Surface(
          shape = RoundedCornerShape(5.dp),
          border = BorderStroke(1.dp, separatorColor()),
          modifier = Modifier.fillMaxWidth()
      ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            text = stringResource(id = R.string.feedbackWaiting),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
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
private fun ChipList(sessionDetails: SessionDetailState) {
  FlowRow(
      modifier = Modifier.padding(top = 16.dp),
      mainAxisSpacing = 8.dp,
  ) {
    sessionDetails.session.language.asLanguageResource()?.let { language ->
      SuggestionChip(
          onClick = {},
          label = { Text(text = language) },
          enabled = true,
          icon = {
            EmojiUtils.getLanguageInEmoji(sessionDetails.session.language)?.let { Text(text = it) }
          }
      )
    }
    for (tag in sessionDetails.session.tags) {
      SuggestionChip(
          onClick = {},
          label = { Text(text = tag) },
          enabled = true
      )
    }
    if (sessionDetails.session.complexity.isNotEmpty()) {
      SuggestionChip(
          onClick = {},
          label = { Text(text = sessionDetails.session.complexity) },
          enabled = true
      )
    }
  }
}

@Composable
private fun Speakers(speakers: List<Speaker>) {
  Column(
      modifier = Modifier.padding(top = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
  ) {
    for (speaker in speakers) {
      Card(
          colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surface,
              contentColor = MaterialTheme.colorScheme.onSurface
          )
      ) {
        Speaker(speaker = speaker)
      }
    }
  }

}

@Composable
private fun Speaker(
    modifier: Modifier = Modifier,
    speaker: Speaker
) {

  Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.Start
  ) {
    AsyncImage(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(speaker.photoUrl)
            .build(),
        contentDescription = stringResource(R.string.title_speakers)
    )

    Text(
        text = speaker.getFullNameAndCompany(),
        style = MaterialTheme.typography.titleLarge,
    )

    speaker.bio?.let { bio ->
      Text(
          modifier = Modifier.padding(),
          text = bio,
          textAlign = TextAlign.Start,
          style = MaterialTheme.typography.bodyMedium,
      )
    }

    Row(
        horizontalArrangement = Arrangement.End
    ) {
      val context = LocalContext.current
      for (socialsItem in speaker.socials.orEmpty().filterNotNull()) {
        IconButton(
            onClick = {
              // TODO Ideally this should not be handled here but by the caller
              openSocialLink(context, socialsItem.link!!)
            }
        ) {
          if (socialsItem.name?.lowercase() == "twitter") {
            Icon(
                painter = painterResource(R.drawable.ic_network_twitter),
                contentDescription = socialsItem.icon
            )
          } else {
            Icon(
                imageVector = Icons.Rounded.Public,
                contentDescription = socialsItem.icon
            )
          }
        }
      }
    }
  }

}


@Composable
private fun getFormattedDateAndRoom(room: Room, startTimestamp: Long, endTimestamp: Long): String {
  val context = LocalContext.current
  val sessionDate = DateUtils.formatDateRange(
      context,
      Formatter(context.resources.configuration.locale),
      startTimestamp,
      endTimestamp,
      DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR,
      "Europe/Paris"
  ).toString()
  return if (room.name.isNotEmpty()) {
    stringResource(R.string.sessionDateWithRoomPlaceholder, sessionDate, room.name)
  } else {
    sessionDate
  }
}

private fun shareSession(
  context: Context,
  session: Session,
  sessionDateAndRoom: String,
  speakersNameList: List<String>
) {
  val speakers = TextUtils.join(", ", speakersNameList)

  val shareSessionIntent = Intent(Intent.ACTION_SEND)
  shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session.title)
  if (speakersNameList.isEmpty()) {
    shareSessionIntent.putExtra(
        Intent.EXTRA_TEXT,
        String.format(
            "%s: %s (%s)",
            context.getString(R.string.app_name),
            session.title,
            sessionDateAndRoom
        )
    )
  } else {
    shareSessionIntent.putExtra(
        Intent.EXTRA_TEXT,
        String.format(
            "%s: %s (%s, %s, %s)",
            context.getString(R.string.app_name),
            session.title,
            speakers,
            sessionDateAndRoom,
            session.language
        )
    )
  }
  shareSessionIntent.type = "text/plain"
  val shareSheetIntent = Intent.createChooser(shareSessionIntent, null)
  context.startActivity(shareSheetIntent)
}

fun openSocialLink(context: Context, link: String) {
  context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}


@Preview
@Composable
private fun SessionDetailLayoutLoadingPreview() {
  SessionDetailLayout(
      sessionDetailState = Lce.Loading,
      onBackClick = {},
      onBookmarkClick = {},
  )
}

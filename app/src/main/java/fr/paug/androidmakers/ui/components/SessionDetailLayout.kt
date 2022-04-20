package fr.paug.androidmakers.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.text.format.DateUtils
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import fr.androidmakers.store.model.*
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.theme.AMColor
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.ui.util.imageUrl
import fr.paug.androidmakers.ui.viewmodel.Lce
import io.openfeedback.android.components.SessionFeedbackContainer
import separatorColor
import surfaceColor2
import java.util.*

class SessionDetailState(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val isBookmarked: Boolean,
)

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
      topBar = {
        TopAppBar(
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
              }
            },
            title = {
              if (sessionDetailState is Lce.Content) {
                Text(sessionDetailState.content.session.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
              }
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
                          speakersList = sessionDetailState.content.speakers,
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
        if (sessionDetailState is Lce.Content) {
          val backgroundColor by animateColorAsState(
              if (sessionDetailState.content.isBookmarked) Color.White else AMColor.amRed
          )
          FloatingActionButton(
              backgroundColor = backgroundColor,
              onClick = {
                onBookmarkClick(!sessionDetailState.content.isBookmarked)
              }
          ) {
            Crossfade(sessionDetailState.content.isBookmarked) { isBookmarked ->
              Image(
                  painterResource(
                      if (isBookmarked) R.drawable.ic_bookmarked_fab else R.drawable.ic_bookmark_fab
                  ),
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
    ChipList(sessionDetails)

    Speakers(sessionDetails.speakers)

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(16.dp))

    if (true || System.currentTimeMillis() > sessionDetails.startTimestamp) {
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
            style = MaterialTheme.typography.body1,
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
      Chip(language)
    }
    for (tag in sessionDetails.session.tags) {
      Chip(tag)
    }
    if (sessionDetails.session.complexity.isNotEmpty()) {
      Chip(sessionDetails.session.complexity)
    }
  }
}

@Composable
private fun Chip(text: String) {
  Text(
      modifier = Modifier
          .background(
              shape = RoundedCornerShape(corner = CornerSize(16.dp)),
              color = surfaceColor2()
          )
          .padding(8.dp),
      text = text
  )
}

@Composable
private fun Speakers(speakers: List<Speaker>) {
  Column(Modifier.padding(top = 16.dp)) {
    for (speaker in speakers) {
      Speaker(speaker)
    }
  }
}

@Composable
private fun Speaker(speaker: Speaker) {
  Column(Modifier.padding(top = 16.dp)) {
    Row() {
      AsyncImage(
          modifier = Modifier
              .size(64.dp)
              .clip(CircleShape),
          placeholder = painterResource(R.drawable.ic_person_black_24dp),
          error = painterResource(R.drawable.ic_person_black_24dp),
          model = ImageRequest.Builder(LocalContext.current)
              .data(speaker.photoUrl?.let { imageUrl(it) })
              .crossfade(true)
              .build(),
          contentDescription = speaker.name
      )

      Column(Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = speaker.getFullNameAndCompany(),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        speaker.bio?.let { bio ->
          Text(
              modifier = Modifier.padding(top = 12.dp),
              text = bio,
              style = MaterialTheme.typography.body2,
          )
        }
      }
    }

    Row(Modifier.padding(start = 58.dp, top = 4.dp)) {
      val context = LocalContext.current
      for (socialsItem in speaker.socials.orEmpty().filterNotNull()) {
        IconButton(
            onClick = {
              // TODO Ideally this should not be handled here but by the caller
              openSocialLink(context, socialsItem.link!!)
            }
        ) {
          Icon(
              painter = painterResource(
                  if (socialsItem.icon == "twitter") {
                    R.drawable.ic_network_twitter
                  } else {
                    R.drawable.ic_network_web
                  }
              ), contentDescription = socialsItem.icon
          )
        }
      }
    }
  }
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

private fun shareSession(context: Context, session: Session, sessionDateAndRoom: String, speakersList: List<Speaker>) {
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

private fun openSocialLink(context: Context, link: String) {
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

@Preview
@Composable
private fun SessionDetailLayoutLoadedPreview() {
  SessionDetailLayout(
      sessionDetailState = Lce.Content(
          SessionDetailState(
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
              speakers = listOf(
                  Speaker(
                      id = "0",
                      badges = listOf(
                          BadgesItem(name = "gdg", link = "https://www.meetup.com/Android-Paris/", description = "Paris Android User Group")
                      ),
                      country = "",
                      featured = false,
                      companyLogo = null,
                      name = "Martin Bonnin",
                      photoUrl = null,
                      bio = "Martin is a maintainer of Apollo Kotlin. He has been writing Android applications since Cupcake and fell in love with Kotlin in 2017. Martin loves naming things and the sound of his laptop fan compiling all these type-safe programs. When not busy rewriting all his bash scripts in Kotlin, Martin loves to hike the Pyrénées or play a good game of Hearthstone.",
                      company = "Apollo GraphQL",
                      socials = listOf(
                          SocialsItem(name = "Website", icon = "website", link = "https://mbonnin.net")
                      ),
                      order = 17
                  ),
                  Speaker(
                      id = "1",
                      badges = null,
                      country = "",
                      featured = false,
                      companyLogo = null,
                      name = "Benoit Lubek",
                      photoUrl = null,
                      bio = "Currently working on Apollo-Kotlin, the Kotlin SDK for GraphQL, Benoit has been writing software for 20 years, with a focus on Android since its v1. When he’s not coding, you can find him enjoying movies or geocaching.",
                      company = "Apollo GraphQL",
                      socials = listOf(
                          SocialsItem(name = "SocialNetworkHandle.SocialNetworkType.Twitter", icon = "twitter", link = "https://twitter.com/BoD"),
                          SocialsItem(name = "Website", icon = "website", link = "https://JRAF.org")
                      ),
                      order = 18
                  )
              ),
              startTimestamp = Date(122, 4, 26, 13, 30).time,
              endTimestamp = Date(122, 4, 26, 14, 15).time,
              isBookmarked = false,
          )
      ),
      onBackClick = {},
      onBookmarkClick = {},
  )
}

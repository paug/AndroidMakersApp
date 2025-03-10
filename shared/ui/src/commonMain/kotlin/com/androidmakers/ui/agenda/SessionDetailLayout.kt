package com.androidmakers.ui.agenda

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.separatorColor
import com.androidmakers.ui.getPlatformContext
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import com.androidmakers.ui.theme.AMColor
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.isAppClinic
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.bookmarked
import fr.paug.androidmakers.ui.english
import fr.paug.androidmakers.ui.feedbackWaiting
import fr.paug.androidmakers.ui.french
import fr.paug.androidmakers.ui.ic_network_blog
import fr.paug.androidmakers.ui.ic_network_linkedin
import fr.paug.androidmakers.ui.ic_network_x
import fr.paug.androidmakers.ui.info
import fr.paug.androidmakers.ui.session_app_clinic_apply
import fr.paug.androidmakers.ui.share
import fr.paug.androidmakers.ui.speakers
import io.openfeedback.OpenFeedback
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SessionDetailScreen(
  viewModel: SessionDetailViewModel,
  onBackClick: () -> Unit,
) {
  val sessionDetailState by viewModel.sessionDetailState.collectAsState()
  val platformContext = getPlatformContext()

  SessionDetailLayout(
    sessionDetailState = sessionDetailState,
    onBackClick = onBackClick,
    onBookmarkClick = {
      viewModel.bookmark(it)
    },
    onShareSession = {
      viewModel.shareSession(platformContext)
    },
    onOpenLink = {
      viewModel.openLink(platformContext, it)
    },
    onApplyForAppClinic = {
      viewModel.applyForAppClinic(platformContext)
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailLayout(
  sessionDetailState: Lce<SessionDetailState>,
  onBackClick: () -> Unit,
  onBookmarkClick: (bookmarked: Boolean) -> Unit,
  onShareSession: () -> Unit,
  onOpenLink: (SocialsItem) -> Unit,
  onApplyForAppClinic: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = onBackClick) {
            Icon(
              Icons.AutoMirrored.Rounded.ArrowBack,
              contentDescription = stringResource(Res.string.back)
            )
          }
        },
        title = {
          // Nothing to do
        },
        actions = {
          if (sessionDetailState is Lce.Content) {
            IconButton(
              onClick = onShareSession
            ) {
              Icon(
                Icons.Rounded.Share,
                contentDescription = stringResource(Res.string.share)
              )
            }
          }
        },
        scrollBehavior = scrollBehavior
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
              contentDescription = stringResource(Res.string.bookmarked)
            )
          }
        }
      }
    }
  ) { innerPadding ->
    Box {
      when (sessionDetailState) {
        is Lce.Loading, Lce.Error -> LoadingLayout(
          modifier = Modifier
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
        )
        is Lce.Content -> SessionDetails(
          sessionDetails = sessionDetailState.content,
          formattedDateAndRoom = sessionDetailState.content.formattedDateAndRoom,
          openLink = onOpenLink,
          onApplyForAppClinic = onApplyForAppClinic,
          modifier = Modifier
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
        )
      }
    }
  }
}

@Composable
private fun SessionDetails(
  modifier: Modifier = Modifier,
  sessionDetails: SessionDetailState,
  formattedDateAndRoom: String,
  openLink: (SocialsItem) -> Unit,
  onApplyForAppClinic: () -> Unit,
) {

  Column(
    Modifier
      .verticalScroll(state = rememberScrollState())
      .padding(16.dp)
      .then(modifier)
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
        contentDescription = stringResource(Res.string.info)
      )

      Text(
        text = formattedDateAndRoom,
        style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp),
      )
    }

    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = sessionDetails.session.description.orEmpty(),
      textAlign = TextAlign.Start,
      style = MaterialTheme.typography.bodyLarge,
    )

    if(sessionDetails.session.isAppClinic()) {
      Button(
        onClick = onApplyForAppClinic,
        modifier = Modifier.padding(top = 16.dp)
          .align(Alignment.CenterHorizontally),
      ) {
        Text(
          text = stringResource(Res.string.session_app_clinic_apply),
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Bold,
        )
      }
    }

    ChipList(sessionDetails)

    Speakers(
      speakers = sessionDetails.speakers,
      openLink = openLink
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(16.dp)
    )

    if (sessionDetails.session.type == "talk") {
      if (Clock.System.now() > sessionDetails.startTimestamp) {
        OpenFeedback(
          projectId = "v6kx3QuQkDU4fX0Ta989",
          sessionId = sessionDetails.session.id,
        )
      } else {
        Surface(
          shape = RoundedCornerShape(5.dp),
          border = BorderStroke(1.dp, separatorColor()),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            text = stringResource(Res.string.feedbackWaiting),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
          )
        }
      }
    }

    // To account for the FAB
    Spacer(modifier = Modifier.height(64.dp))
  }

}

@Composable
private fun String.asLanguageResource(): String? {
  return when (this) {
    "English" -> stringResource(Res.string.english)
    "French" -> stringResource(Res.string.french)
    else -> null
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipList(sessionDetails: SessionDetailState) {
  FlowRow(
    modifier = Modifier.padding(top = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
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
    sessionDetails.session.complexity?.let { complexity ->
      SuggestionChip(
        onClick = {},
        label = { Text(complexity.name) },
        enabled = true
      )
    }
  }
}

@Composable
private fun Speakers(
  speakers: List<Speaker>,
  openLink: (SocialsItem) -> Unit,
) {
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
        Speaker(
          speaker = speaker,
          openLink = openLink
        )
      }
    }
  }
}

@Composable
private fun Speaker(
  modifier: Modifier = Modifier,
  speaker: Speaker,
  openLink: (SocialsItem) -> Unit,
) {

  Column(
    modifier = modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.Start
  ) {
    speaker.photoUrl?.let { speakerPhotoUrl ->
      AsyncImage(
        model = speakerPhotoUrl,
        modifier = Modifier
          .size(64.dp)
          .clip(CircleShape),
        contentDescription = stringResource(Res.string.speakers)
      )
    }

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

    SocialButtons(
      speaker = speaker,
      openLink = openLink
    )
  }
}

@Composable
fun SocialButtons(
  speaker: Speaker,
  openLink: (SocialsItem) -> Unit,
) {
  Row(
    horizontalArrangement = Arrangement.End
  ) {
    //val context = LocalContext.current
    for (socialsItem in speaker.socials.orEmpty().filterNotNull()) {
      IconButton(
        onClick = { openLink(socialsItem) }
      ) {
        val socialName = socialsItem.name?.lowercase().orEmpty()
        when {
          socialName.contains("twitter") || socialName == "x" -> {
            Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(Res.drawable.ic_network_x),
              contentDescription = socialsItem.name
            )
          }

          socialName.contains("blog") -> {
            Icon(
              painter = painterResource(Res.drawable.ic_network_blog),
              contentDescription = socialsItem.name
            )
          }

          socialName.contains("linkedin") -> {
            Icon(
              painter = painterResource(Res.drawable.ic_network_linkedin),
              contentDescription = socialsItem.name
            )
          }

          else -> {
            Icon(
              imageVector = Icons.Rounded.Public,
              contentDescription = socialsItem.name
            )
          }
        }
      }
    }
  }
}


@Preview
@Composable
private fun SessionDetailLayoutLoadingPreview() {
  SessionDetailLayout(
    sessionDetailState = Lce.Loading,
    onBackClick = {},
    onBookmarkClick = {},
    onShareSession = {},
    onOpenLink = {},
    onApplyForAppClinic = {}
  )
}

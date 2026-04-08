package com.androidmakers.ui.speakers

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.SocialButtons
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.theme.LocalIsNeobrutalism
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.ic_arrow_back
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.speakers
import fr.paug.androidmakers.ui.talks
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun SpeakerDetailsRoute(
    speakerDetailsViewModel: SpeakerDetailsViewModel,
    onBackClick: () -> Unit,
    onSessionClick: (sessionId: String) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val uiState by speakerDetailsViewModel.uiState.collectAsStateWithLifecycle()
  when (val state = uiState) {
    Lce.Loading -> LoadingLayout()
    is Lce.Error -> {

    }

    is Lce.Content -> {
      val urlOpener = LocalUriHandler.current.toUrlOpener()
      SpeakerDetailsScreen(
        uiState = state.content,
        onSocialItemClick = { speakerDetailsViewModel.openSpeakerLink(urlOpener, it) },
        onBackClick = onBackClick,
        onSessionClick = onSessionClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerDetailsScreen(
    uiState: SpeakerDetailsUiState,
    onSocialItemClick: (SocialsItem) -> Unit,
    onBackClick: () -> Unit,
    onSessionClick: (sessionId: String) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val speaker = uiState.speaker
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
            ),
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = stringResource(Res.string.back)
                )
              }
            },
            title = {
              // Nothing to do
            },
            scrollBehavior = scrollBehavior
        )
      }
  ) { innerPadding ->
    Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
          .padding(innerPadding)
          .consumeWindowInsets(innerPadding)
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

      val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
      speaker.photoUrl?.let { photoUrl ->
        val photoModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
          with(sharedTransitionScope) {
            Modifier.sharedElement(
              sharedContentState = rememberSharedContentState(key = "speaker_photo_${speaker.id}"),
              animatedVisibilityScope = animatedVisibilityScope,
            )
          }
        } else {
          Modifier
        }
        AsyncImage(
            model = photoUrl,
            modifier = photoModifier
                .size(64.dp)
                .clip(circularShape),
            contentDescription = stringResource(Res.string.speakers)
        )
      }

      SelectionContainer {
        Text(
            text = speaker.name.orEmpty(),
            style = MaterialTheme.typography.headlineLarge
        )
      }

      SelectionContainer {
        Text(
            text = speaker.bio.orEmpty(),
            textAlign = TextAlign.Start,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.75.sp,
            hyphens = Hyphens.Auto,
            lineBreak = LineBreak.Paragraph,
          )
        )
      }

      SocialButtons(
          speaker = speaker,
          onClickOnItem = onSocialItemClick
      )

      if (uiState.sessions.isNotEmpty()) {
        SpeakerTalksSection(
          sessions = uiState.sessions,
          onSessionClick = onSessionClick,
        )
      }
    }
  }
}

@Composable
private fun SpeakerTalksSection(
  sessions: List<Session>,
  onSessionClick: (sessionId: String) -> Unit,
) {
  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(
      text = stringResource(Res.string.talks),
      style = MaterialTheme.typography.titleMedium,
    )
    sessions.forEach { session ->
      ElevatedCard(
        modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
        onClick = { onSessionClick(session.id) },
        shape = MaterialTheme.shapes.large,
      ) {
        Text(
          modifier = Modifier.padding(16.dp),
          text = session.title,
          style = MaterialTheme.typography.bodyLarge,
        )
      }
    }
  }
}

// region Previews

private val previewSpeaker = Speaker(
  id = "speaker-1",
  name = "Ada Lovelace",
  company = "Babbage & Co.",
  bio = "Mathematician and writer, chiefly known for her work on Charles Babbage's proposed mechanical general-purpose computer, the Analytical Engine.",
  photoUrl = null,
  socials = listOf(
    SocialsItem(name = "Twitter", url = "https://twitter.com/ada"),
  ),
)

private val previewSessions = listOf(
  Session(
    id = "session-1",
    title = "The First Algorithm: A Deep Dive into Analytical Engine Programming",
    speakers = listOf("speaker-1"),
    startsAt = LocalDateTime(2026, 4, 9, 9, 0),
    endsAt = LocalDateTime(2026, 4, 9, 9, 45),
    roomId = "room-1",
    isServiceSession = false,
    type = "talk",
  ),
  Session(
    id = "session-2",
    title = "Beyond Numbers: Mathematical Imagination in the 19th Century",
    speakers = listOf("speaker-1"),
    startsAt = LocalDateTime(2026, 4, 10, 11, 0),
    endsAt = LocalDateTime(2026, 4, 10, 11, 45),
    roomId = "room-2",
    isServiceSession = false,
    type = "talk",
  ),
)

@Preview
@Composable
private fun SpeakerDetailsScreenPreview() {
  SpeakerDetailsScreen(
    uiState = SpeakerDetailsUiState(
      speaker = previewSpeaker,
      sessions = previewSessions,
    ),
    onSocialItemClick = {},
    onBackClick = {},
    onSessionClick = {},
  )
}

@Preview
@Composable
private fun SpeakerDetailsScreenNoTalksPreview() {
  SpeakerDetailsScreen(
    uiState = SpeakerDetailsUiState(
      speaker = previewSpeaker,
      sessions = emptyList(),
    ),
    onSocialItemClick = {},
    onBackClick = {},
    onSessionClick = {},
  )
}

// endregion

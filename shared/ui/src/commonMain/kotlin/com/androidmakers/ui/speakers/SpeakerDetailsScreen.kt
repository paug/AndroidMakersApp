package com.androidmakers.ui.speakers

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.SocialButtons
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.model.SocialsItem
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.speakers
import org.jetbrains.compose.resources.stringResource


@Composable
fun SpeakerDetailsRoute(
    speakerDetailsViewModel: SpeakerDetailsViewModel,
    onBackClick: () -> Unit,
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
                    Icons.AutoMirrored.Rounded.ArrowBack,
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
                .clip(CircleShape),
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
            style = MaterialTheme.typography.bodyLarge,
        )
      }

      SocialButtons(
          speaker = speaker,
          onClickOnItem = onSocialItemClick
      )
    }
  }
}

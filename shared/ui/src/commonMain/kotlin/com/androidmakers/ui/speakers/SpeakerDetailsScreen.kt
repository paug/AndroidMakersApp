package com.androidmakers.ui.speakers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.LocalPlatformContext
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.SocialButtons
import com.androidmakers.ui.model.Lce
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.SocialsItem
import fr.paug.androidmakers.ui.MR


@Composable
fun SpeakerDetailsRoute(
    modifier: Modifier = Modifier,
    speakerDetailsViewModel: SpeakerDetailsViewModel,
    onBackClick: () -> Unit,
) {
  val uiState by speakerDetailsViewModel.uiState.collectAsState()
  when (val state = uiState) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> {

    }

    is Lce.Content -> {
      val platformContext = LocalPlatformContext.current
      SpeakerDetailsScreen(
        uiState = state.content,
        onSocialItemClick = { speakerDetailsViewModel.openSpeakerLink(platformContext, it) },
        onBackClick = onBackClick
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
) {
  val speaker = uiState.speaker
  Scaffold(
      topBar = {
        TopAppBar(
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(MR.strings.back)
                )
              }
            },
            title = {
              // Nothing to do
            }
        )
      }
  ) { innerPadding ->
    Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

      speaker.photoUrl?.let { photoUrl ->
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            painter = rememberImagePainter(photoUrl),
            contentDescription = stringResource(MR.strings.speakers)
        )
      }

      Text(
          text = speaker.name.orEmpty(),
          style = MaterialTheme.typography.headlineLarge
      )

      Text(
          text = speaker.bio.orEmpty(),
          textAlign = TextAlign.Start,
          style = MaterialTheme.typography.bodyLarge,
      )

      SocialButtons(
          speaker = speaker,
          onClickOnItem = onSocialItemClick
      )
    }
  }
}

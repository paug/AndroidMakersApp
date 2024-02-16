package fr.paug.androidmakers.ui.components.speakers.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.components.session.SocialButtons
import fr.paug.androidmakers.ui.viewmodel.Lce

@Composable
fun SpeakerDetailsRoute(
    modifier: Modifier = Modifier,
    speakerDetailsViewModel: SpeakerDetailsViewModel,
    onBackClick: () -> Unit,
) {
  val uiState by speakerDetailsViewModel.uiState.collectAsStateWithLifecycle()
  when (val state = uiState) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> {

    }

    is Lce.Content -> {
      SpeakerDetailsScreen(
          uiState = state.content,
          onBackClick = onBackClick
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerDetailsScreen(
    uiState: SpeakerDetailsUiState,
    onBackClick: () -> Unit,
) {
  val speaker = uiState.speaker
  Scaffold(
      modifier = Modifier.navigationBarsPadding(),
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        TopAppBar(
            navigationIcon = {
              IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back)
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
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
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
          text = speaker.name.orEmpty(),
          style = MaterialTheme.typography.headlineLarge
      )

      Text(
          text = speaker.bio.orEmpty(),
          textAlign = TextAlign.Start,
          style = MaterialTheme.typography.bodyLarge,
      )

      SocialButtons(speaker)
    }
  }
}

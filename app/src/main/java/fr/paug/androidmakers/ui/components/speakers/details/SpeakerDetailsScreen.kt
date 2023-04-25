package fr.paug.androidmakers.ui.components.speakers.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.components.session.openSocialLink
import fr.paug.androidmakers.ui.viewmodel.Lce

@Composable
fun SpeakerDetailsRoute(
    modifier: Modifier = Modifier,
    speakerId: String
) {
  val speakerDetailsViewModel: SpeakerDetailsViewModel = viewModel(
      factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
          @Suppress("UNCHECKED_CAST")
          return SpeakerDetailsViewModel(
              speakerId = speakerId
          ) as T
        }
      }
  )

  val uiState by speakerDetailsViewModel.uiState.collectAsStateWithLifecycle()
  when (val state = uiState) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> { }
    is Lce.Content -> {
      SpeakerDetailsScreen(
          uiState = state.content
      )
    }
  }
}

@Composable
fun SpeakerDetailsScreen(
    uiState: SpeakerDetailsUiState
) {
  val speaker = uiState.speaker

  Column(
      modifier = Modifier
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

    Row(
        horizontalArrangement = Arrangement.End
    ) {
      val context = LocalContext.current
      for (socialsItem in speaker.socials.orEmpty().filterNotNull()) {
        IconButton(
            onClick = {
              openSocialLink(context, socialsItem.link!!)
            }
        ) {
          if (socialsItem.icon == "twitter") {
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
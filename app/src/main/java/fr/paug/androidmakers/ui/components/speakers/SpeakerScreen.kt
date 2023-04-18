package fr.paug.androidmakers.ui.components.speakers

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.viewmodel.Lce

@Composable
fun SpeakerScreen(
    modifier: Modifier = Modifier,
    speakerViewModel: SpeakerViewModel,
) {

  val speakersUiState by speakerViewModel.uiState.collectAsState(
      initial = Lce.Loading
  )

  when (val state = speakersUiState) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> {

    }

    is Lce.Content -> {
      LazyColumn {
        items(state.content.speakers) { speaker ->
          SpeakerItem(
              speaker = speaker
          )
        }
      }
    }

  }

}

@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker) {
  ListItem(
      modifier = modifier,
      headlineContent = {
        Text(
            text = speaker.name.orEmpty(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
      },
      supportingContent = {
        Text(
            text = speaker.bio.orEmpty(),
            style = MaterialTheme.typography.labelMedium,
        )
      },
      leadingContent = {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(speaker.photoUrl)
                .build(),
            contentDescription = stringResource(R.string.title_speakers)
        )
      },
      trailingContent = {},
//      colors =,
//      tonalElevation =,
//      shadowElevation =
  )
}


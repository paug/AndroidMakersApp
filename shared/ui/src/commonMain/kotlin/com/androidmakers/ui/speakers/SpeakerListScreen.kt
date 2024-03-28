package com.androidmakers.ui.speakers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.model.Lce
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerScreen(
    modifier: Modifier = Modifier,
    viewModel: SpeakerListViewModel,
    navigateToSpeakerDetails: (String) -> Unit,
) {

  val state by viewModel.uiState.collectAsState(Lce.Loading)

  when (state) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> {

    }

    is Lce.Content -> {

      var text by rememberSaveable { mutableStateOf("") }
      var active by rememberSaveable { mutableStateOf(false) }
      val content = (state as Lce.Content<SpeakersUiState>).content

      Box(Modifier.fillMaxSize()) {
        Box(Modifier
            .semantics { isTraversalGroup = true }
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 8.dp)
            .zIndex(1f)
            .fillMaxWidth()) {

          SearchBar(
              modifier = Modifier.align(Alignment.TopCenter),
              query = text,
              onQueryChange = { text = it },
              onSearch = { active = false },
              active = active,
              colors = SearchBarDefaults.colors(
                  containerColor = MaterialTheme.colorScheme.surface,
                  dividerColor = MaterialTheme.colorScheme.primary,
              ),
              onActiveChange = {
                active = it
              },
              windowInsets = WindowInsets(0, 0, 0, 0),
              placeholder = { Text(stringResource(MR.strings.speaker_search_placeholder)) },
              leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) }
          ) {
            LazyColumn {
              val speakers = content.speakers
              items(speakers.filter { it.name?.contains(text, ignoreCase = true) == true }) { speaker ->
                SpeakerItem(
                    speaker = speaker,
                    navigateToSpeakerDetails = navigateToSpeakerDetails,
                )
              }
            }
          }
        }

        AnimatedVisibility(
            visible = !active,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
          LazyColumn(
              contentPadding = PaddingValues(start = 0.dp, top = 72.dp, end = 0.dp, bottom = 16.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(content.speakers.filter { it.name?.contains(text, ignoreCase = true) == true }) { speaker ->
              SpeakerItem(
                  speaker = speaker,
                  navigateToSpeakerDetails = navigateToSpeakerDetails
              )
            }
          }
        }
      }
    }

  }

}

@Composable
fun SpeakerItem(
    modifier: Modifier = Modifier,
    speaker: Speaker,
    navigateToSpeakerDetails: (String) -> Unit,
) {

  ListItem(
      modifier = modifier.clickable(onClick = { navigateToSpeakerDetails(speaker.id) }),
      colors = ListItemDefaults.colors(
          containerColor = MaterialTheme.colorScheme.background,
      ),
      headlineContent = {
        Text(
            text = speaker.name.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
        )
      },
      supportingContent = speaker.company?.let { company ->
        {
          Text(
              text = company,
              style = MaterialTheme.typography.labelMedium,
          )
        }
      },
      leadingContent = {
        speaker.photoUrl?.let { url ->
          Image(
              modifier = Modifier
                  .size(64.dp)
                  .clip(CircleShape),
              painter = rememberImagePainter(url),
              contentDescription = stringResource(MR.strings.speakers)
          )
        }
      },
      trailingContent = {},
  )
}

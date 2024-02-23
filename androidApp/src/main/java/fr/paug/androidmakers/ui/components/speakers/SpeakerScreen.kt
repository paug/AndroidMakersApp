package fr.paug.androidmakers.ui.components.speakers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.androidmakers.store.model.Speaker
import fr.androidmakers.store.model.SpeakerId
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.viewmodel.Lce

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerScreen(
    modifier: Modifier = Modifier,
    speakerViewModel: SpeakerViewModel,
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {

  val speakersUiState by speakerViewModel.uiState.collectAsState(
      initial = Lce.Loading
  )

  when (val state = speakersUiState) {
    Lce.Loading -> LoadingLayout()
    Lce.Error -> {

    }

    is Lce.Content -> {

      var text by rememberSaveable { mutableStateOf("") }
      var active by rememberSaveable { mutableStateOf(false) }

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
//                  inputFieldColors = SearchBarDefaults.inputFieldColors(
//                      focusedTextColor = MaterialTheme.colorScheme.surface,
////                      unfocusedTextColor =,
////                      disabledTextColor =,
////                      cursorColor =,
////                      selectionColors =,
////                      focusedLeadingIconColor =,
////                      unfocusedLeadingIconColor =,
////                      disabledLeadingIconColor =,
////                      focusedTrailingIconColor =,
////                      unfocusedTrailingIconColor =,
////                      disabledTrailingIconColor =,
////                      focusedPlaceholderColor =,
////                      unfocusedPlaceholderColor =,
////                      disabledPlaceholderColor =
//                  ),
              ),
              onActiveChange = {
                active = it
              },
              windowInsets = WindowInsets(0, 0, 0, 0),
              placeholder = { Text(stringResource(id = R.string.speaker_search_placeholder)) },
              leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) }
          ) {
            LazyColumn {
              items(state.content.speakers.filter { it.name?.contains(text, ignoreCase = true) == true }) { speaker ->
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
            items(state.content.speakers.filter { it.name?.contains(text, ignoreCase = true) == true }) { speaker ->
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
    navigateToSpeakerDetails: (SpeakerId) -> Unit,
) {

  ListItem(
      modifier = modifier.clickable(onClick = { navigateToSpeakerDetails(speaker.id) }),
      colors = ListItemDefaults.colors(
          containerColor = MaterialTheme.colorScheme.background,
      ),
      headlineContent = {
        Text(
            text = speaker.name.orEmpty(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
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
  )
}

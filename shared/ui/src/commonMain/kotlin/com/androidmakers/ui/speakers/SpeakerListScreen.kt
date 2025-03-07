package com.androidmakers.ui.speakers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.speaker_search_placeholder
import fr.paug.androidmakers.ui.speakers
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerScreen(
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
      var expanded by rememberSaveable { mutableStateOf(false) }
      val content = (state as Lce.Content<SpeakersUiState>).content

      val filteredSpeakers = content.speakers.filter { it.name?.contains(text, ignoreCase = true) == true }

      var searchHeight by remember { mutableStateOf(56.dp) }

      Box(Modifier.fillMaxSize()) {

        val density = LocalDensity.current

        AnimatedVisibility(
            visible = !expanded,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
          LazyColumn(
              contentPadding = PaddingValues(top = searchHeight + 16.dp),
              verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(filteredSpeakers) { speaker ->
              SpeakerItem(
                  speaker = speaker,
                  navigateToSpeakerDetails = navigateToSpeakerDetails
              )
            }
          }
        }

        val horizontalPadding: Dp by animateDpAsState(if (expanded) 0.dp else 24.dp)
        val hasQuery by remember { derivedStateOf { text.isNotEmpty() } }

        SearchBar(
          inputField = {
            SearchBarDefaults.InputField(
              modifier = Modifier.fillMaxWidth(),
              query = text,
              onQueryChange = { text = it },
              onSearch = { expanded = false },
              expanded = expanded,
              onExpandedChange = { expanded = it },
              placeholder = { Text(stringResource(Res.string.speaker_search_placeholder)) },
              leadingIcon = {
                if (expanded) {
                  IconButton(onClick = { expanded = false }) {
                    Icon(
                      Icons.AutoMirrored.Rounded.ArrowBack,
                      contentDescription = stringResource(Res.string.back)
                    )
                  }
                } else {
                  Icon(Icons.Rounded.Search, contentDescription = null)
                }
              },
              trailingIcon = {
                if (hasQuery) {
                  IconButton(onClick = { text = "" }) {
                    Icon(Icons.Rounded.Clear, contentDescription = null)
                  }
                }
              }
            )
          },
          expanded = expanded,
          onExpandedChange = { expanded = it },
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(horizontal = horizontalPadding)
            .onSizeChanged {
              searchHeight = with(density) { it.height.toDp() }
            },
          colors = SearchBarDefaults.colors(
            dividerColor = MaterialTheme.colorScheme.primary
          )
        ) {
          LazyColumn {
            items(
              items = filteredSpeakers,
              key = { it.id }
            ) { speaker ->
              SpeakerItem(
                speaker = speaker,
                modifier = Modifier.animateItem(),
                navigateToSpeakerDetails = navigateToSpeakerDetails,
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
    speaker: Speaker,
    modifier: Modifier = Modifier,
    navigateToSpeakerDetails: (String) -> Unit,
) {

  ListItem(
      modifier = modifier.clickable(onClick = { navigateToSpeakerDetails(speaker.id) }),
      colors = ListItemDefaults.colors(
          containerColor = Color.Transparent,
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
          AsyncImage(
              model = url,
              modifier = Modifier
                  .size(64.dp)
                  .clip(CircleShape),
              contentDescription = stringResource(Res.string.speakers)
          )
        }
      }
  )
}

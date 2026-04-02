package com.androidmakers.ui.speakers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.theme.LocalIsNeobrutalism
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.ic_arrow_back
import fr.paug.androidmakers.ui.ic_clear
import fr.paug.androidmakers.ui.ic_search
import fr.paug.androidmakers.ui.speaker_search_placeholder
import fr.paug.androidmakers.ui.speakers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerScreen(
  viewModel: SpeakerListViewModel,
  navigateToSpeakerDetails: (String) -> Unit,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val state by viewModel.uiState.collectAsStateWithLifecycle()

  when (state) {
    Lce.Loading -> LoadingLayout()
    is Lce.Error -> {

    }

    is Lce.Content -> {
      var text by rememberSaveable { mutableStateOf("") }
      var expanded by rememberSaveable { mutableStateOf(false) }

      val speakers = (state as Lce.Content<SpeakersUiState>).content.speakers
      val filteredSpeakers = remember(speakers, text) {
        speakers.filter { it.name?.contains(text, ignoreCase = true) == true }
      }

      var searchHeight by remember { mutableStateOf(56.dp) }

      Box(Modifier.fillMaxSize()) {
        SpeakerListContent(
          visible = !expanded,
          speakers = filteredSpeakers,
          searchHeight = searchHeight,
          navigateToSpeakerDetails = navigateToSpeakerDetails,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )

        SpeakerSearchBar(
          text = text,
          onTextChange = { text = it },
          expanded = expanded,
          onExpandedChange = { expanded = it },
          filteredSpeakers = filteredSpeakers,
          navigateToSpeakerDetails = navigateToSpeakerDetails,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
          onSearchHeightChanged = { searchHeight = it },
          modifier = Modifier.align(Alignment.TopCenter),
        )
      }
    }

  }
}

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpeakerSearchBar(
  text: String,
  onTextChange: (String) -> Unit,
  expanded: Boolean,
  onExpandedChange: (Boolean) -> Unit,
  filteredSpeakers: List<Speaker>,
  navigateToSpeakerDetails: (String) -> Unit,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
  onSearchHeightChanged: (Dp) -> Unit,
  modifier: Modifier = Modifier,
) {
  val density = LocalDensity.current
  val horizontalPadding: Dp by animateDpAsState(if (expanded) 0.dp else 24.dp)
  val hasQuery by remember { derivedStateOf { text.isNotEmpty() } }

  SearchBar(
    inputField = {
      SearchBarDefaults.InputField(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = onTextChange,
        onSearch = { onExpandedChange(false) },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        placeholder = { Text(stringResource(Res.string.speaker_search_placeholder)) },
        leadingIcon = {
          if (expanded) {
            IconButton(onClick = { onExpandedChange(false) }) {
              Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = stringResource(Res.string.back)
              )
            }
          } else {
            Icon(painter = painterResource(Res.drawable.ic_search), contentDescription = null)
          }
        },
        trailingIcon = {
          if (hasQuery) {
            IconButton(onClick = { onTextChange("") }) {
              Icon(painter = painterResource(Res.drawable.ic_clear), contentDescription = null)
            }
          }
        }
      )
    },
    expanded = expanded,
    onExpandedChange = onExpandedChange,
    modifier = modifier
      .padding(horizontal = horizontalPadding)
      .onSizeChanged {
        onSearchHeightChanged(with(density) { it.height.toDp() })
      },
    colors = SearchBarDefaults.colors(
      dividerColor = MaterialTheme.colorScheme.primary
    )
  ) {
    LazyColumn(
      contentPadding = PaddingValues(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(
        items = filteredSpeakers,
        key = { it.id }
      ) { speaker ->
        SpeakerItem(
          speaker = speaker,
          modifier = Modifier.animateItem(),
          navigateToSpeakerDetails = navigateToSpeakerDetails,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )
      }
    }
  }
}

@Composable
private fun SpeakerListContent(
  visible: Boolean,
  speakers: List<Speaker>,
  searchHeight: Dp,
  navigateToSpeakerDetails: (String) -> Unit,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
) {
  AnimatedVisibility(
    visible = visible,
    enter = fadeIn(),
    exit = fadeOut(),
  ) {
    LazyColumn(
      contentPadding = PaddingValues(
        start = 16.dp,
        end = 16.dp,
        top = searchHeight + 16.dp,
      ),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(speakers) { speaker ->
        SpeakerItem(
          speaker = speaker,
          navigateToSpeakerDetails = navigateToSpeakerDetails,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )
      }
    }
  }
}

@Composable
fun SpeakerItem(
  speaker: Speaker,
  modifier: Modifier = Modifier,
  navigateToSpeakerDetails: (String) -> Unit,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {

  val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .neoBrutalElevation()
      .clickable(onClick = { navigateToSpeakerDetails(speaker.id) }),
    shape = MaterialTheme.shapes.large,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      speaker.photoUrl?.let { url ->
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
          model = url,
          modifier = photoModifier
            .size(56.dp)
            .clip(circularShape),
          contentDescription = stringResource(Res.string.speakers)
        )
      }
      Column {
        Text(
          text = speaker.name.orEmpty(),
          style = MaterialTheme.typography.titleMedium,
        )
        speaker.company?.let { company ->
          Text(
            text = company,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

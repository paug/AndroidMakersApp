package com.androidmakers.ui.speakers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.Speaker
import fr.paug.androidmakers.ui.MR

@Composable
fun SpeakersGridView(
  modifier: Modifier = Modifier,
  speakers: List<Speaker>,
  navigateToSpeakerDetails: (Speaker) -> Unit,
) {
    LazyVerticalGrid(
      contentPadding = PaddingValues(start = 32.dp, top = 72.dp, end = 32.dp, bottom = 32.dp),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      columns = GridCells.Adaptive(200.dp),
    ) {
      items(speakers) { speaker ->
        BigSpeakerItem(
          modifier = Modifier.padding(16.dp),
          speaker = speaker,
          navigateToSpeakerDetails = navigateToSpeakerDetails
        )
      }
    }
}

@Composable
fun BigSpeakerItem(
  modifier: Modifier = Modifier,
  speaker: Speaker,
  navigateToSpeakerDetails: (Speaker) -> Unit,
) {

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.clickable(onClick = { navigateToSpeakerDetails(speaker) })
      .then(modifier)
  ) {

    speaker.photoUrl?.let { url ->
      Image(
        modifier = Modifier
          .clip(RoundedCornerShape(16.dp))
          .fillMaxWidth()
          .aspectRatio(1f),
        contentScale = ContentScale.Crop,
        painter = rememberImagePainter(url),
        contentDescription = stringResource(MR.strings.speakers)
      )
    }

    Text(
      text = speaker.name.orEmpty(),
      style = MaterialTheme.typography.titleLarge,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(top = 16.dp)
    )

    speaker.company?.let { company ->
      Text(
        text = company,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
      )
    }
  }
}

package com.androidmakers.ui.venue

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.UIVenue
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.utils.removeHtmlTags
import fr.paug.androidmakers.ui.MR

@Composable
fun VenueLayout(
    uiVenue: UIVenue,
    onClickOnMap: (String) -> Unit
) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
          .verticalScroll(rememberScrollState())
  ) {
    val painter = rememberImagePainter(uiVenue.imageUrl)
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    )
    Text(
        text = uiVenue.name,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.headlineMedium,
    )
    if (uiVenue.address != null) {
      Text(
          text = uiVenue.address,
          modifier = Modifier.padding(8.dp),
          style = MaterialTheme.typography.titleMedium,
      )
    }
    val description = if (Locale.current.language.lowercase().contains("fr")) {
      uiVenue.descriptionFr
    } else {
      uiVenue.descriptionEn
    }
    Text(
        text = description.removeHtmlTags(),
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
    )

    Button(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        onClick = { uiVenue.coordinates?.let { onClickOnMap(it) } }) {
      Text(text = stringResource(MR.strings.locate_on_map))
    }
  }
}

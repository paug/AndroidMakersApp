package com.androidmakers.ui.venue

import androidx.compose.foundation.layout.Column
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
import coil3.compose.AsyncImage
import com.androidmakers.ui.model.UIVenue
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.locate_on_map
import org.jetbrains.compose.resources.stringResource

@Composable
fun VenueLayout(
    uiVenue: UIVenue,
    onClickOnMap: (String) -> Unit
) {
  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
  ) {
    AsyncImage(
        model = uiVenue.imageUrl,
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
        text = description,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
    )

    Button(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        onClick = { uiVenue.coordinates?.let { onClickOnMap(it) } }) {
      Text(text = stringResource(Res.string.locate_on_map))
    }
  }
}

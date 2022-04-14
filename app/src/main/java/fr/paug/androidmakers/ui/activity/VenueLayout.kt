package fr.paug.androidmakers.ui.activity

import android.text.Html
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.paug.androidmakers.ui.model.UIVenue
import fr.paug.androidmakers.ui.theme.AMColor

@Composable
fun VenueLayout(uiVenue: UIVenue) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight()
          .verticalScroll(rememberScrollState())
  ) {
    AsyncImage(
        model = uiVenue.imageUrl,
        placeholder = ColorPainter(AMColor.lightGray),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    )
    Text(
        text = uiVenue.name,
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.h5,
    )
    if (uiVenue.address != null) {
      Text(
          text = uiVenue.address,
          modifier = Modifier.padding(8.dp),
          style = MaterialTheme.typography.subtitle1,
      )
    }
    val description = if (Locale.current.language.lowercase().contains("fr")) {
        uiVenue.descriptionFr
    } else {
        uiVenue.descriptionEn
    }
    Text(
        text = Html.fromHtml(description).toString(),
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.body1,
    )
  }
}

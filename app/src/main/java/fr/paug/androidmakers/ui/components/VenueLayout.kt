package fr.paug.androidmakers.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.google.android.material.snackbar.Snackbar
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.model.UIVenue
import fr.paug.androidmakers.ui.util.discardHtmlTags
import fr.paug.androidmakers.util.CustomTabUtil
import surfaceColor2

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
        placeholder = ColorPainter(surfaceColor2()),
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
        text = description.discardHtmlTags(),
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.body1,
    )
      val context = LocalContext.current
      Button(modifier = Modifier.fillMaxSize(),
          onClick = { openMap(context, uiVenue.coordinates, uiVenue.name) }) {
          Text(text = stringResource(R.string.locate_on_map))
      }
  }
}

fun openMap(context: Context, coordinates: String?, name: String) {
    val venueCoordinatesUri = Uri.parse("geo:" + coordinates +
            "?q=" + Uri.encode(name))
    try {
        val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, R.string.no_maps_app_found, Toast.LENGTH_SHORT).show()
        // Open in Webview
        CustomTabUtil.openChromeTab(context, "https://www.google.com/maps/?q=" + coordinates?.replace(" ", ""))
    }
}


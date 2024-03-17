package fr.androidmakers.domain.interactor

import android.content.Context
import android.content.Intent
import android.net.Uri
import fr.androidmakers.domain.utils.UrlOpener

actual class OpenMapUseCase(
    private val context: Context,
    private val urlOpener: UrlOpener
) {
  actual operator fun invoke(coordinates: String, name: String) {
    val venueCoordinatesUri = Uri.parse(
        "geo:" + coordinates +
            "?q=" + Uri.encode(name)
    )
    try {
      val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
      context.startActivity(intent)
    } catch (e: Exception) {
      // Open in Webview
      urlOpener.openUrl(
          url = "https://www.google.com/maps/?q=" + coordinates.replace(" ", "")
      )
    }
  }
}

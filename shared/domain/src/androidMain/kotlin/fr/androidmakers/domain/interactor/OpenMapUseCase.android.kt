package fr.androidmakers.domain.interactor

import android.content.Intent
import android.net.Uri
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.UrlOpener

actual class OpenMapUseCase(
    private val urlOpener: UrlOpener
) {
  actual operator fun invoke(platformContext: PlatformContext, coordinates: String, name: String) {
    val venueCoordinatesUri = Uri.parse(
        "geo:" + coordinates +
            "?q=" + Uri.encode(name)
    )
    try {
      val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
      platformContext.context.startActivity(intent)
    } catch (e: Exception) {
      // Open in Webview
      urlOpener.openUrl(
        platformContext = platformContext,
        url = "https://www.google.com/maps/?q=" + coordinates.replace(" ", "")
      )
    }
  }
}

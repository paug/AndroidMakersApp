package fr.androidmakers.domain.interactor

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import fr.androidmakers.domain.PlatformContext

actual class OpenMapUseCase() {
  actual operator fun invoke(platformContext: PlatformContext, coordinates: String, name: String) {
    val venueCoordinatesUri = Uri.Builder()
      .scheme("geo")
      .encodedAuthority(coordinates)
      .appendQueryParameter("q", name)
      .build()
    try {
      val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
      platformContext.context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
      // Open in browser
      val fallbackUri = ("https://www.google.com/maps/?q=" + coordinates.filter { it != ' ' }).toUri()
      try {
        val intent = Intent(Intent.ACTION_VIEW, fallbackUri)
        platformContext.context.startActivity(intent)
      } catch (_: ActivityNotFoundException) {
      }
    }
  }
}

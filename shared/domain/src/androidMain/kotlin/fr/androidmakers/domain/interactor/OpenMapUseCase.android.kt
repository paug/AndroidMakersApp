package fr.androidmakers.domain.interactor

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

actual class OpenMapUseCase() {
  actual operator fun invoke(context: Context, coordinates: String, name: String) {
    val venueCoordinatesUri = Uri.Builder()
      .scheme("geo")
      .encodedAuthority(coordinates)
      .appendQueryParameter("q", name)
      .build()
    try {
      val intent = Intent(Intent.ACTION_VIEW, venueCoordinatesUri)
      context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
      // Open in browser
      val fallbackUri = ("https://www.google.com/maps/?q=" + coordinates.filter { it != ' ' }).toUri()
      try {
        val intent = Intent(Intent.ACTION_VIEW, fallbackUri)
        context.startActivity(intent)
      } catch (_: ActivityNotFoundException) {
      }
    }
  }
}

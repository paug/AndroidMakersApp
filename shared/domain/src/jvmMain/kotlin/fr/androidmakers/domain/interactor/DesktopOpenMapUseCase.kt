package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import java.awt.Desktop
import java.net.URI

class DesktopOpenMapUseCase : OpenMapUseCase {
  override operator fun invoke(context: PlatformContext, coordinates: String, name: String) {
    try {
      val cleanCoordinates = coordinates.filter { it != ' ' }
      Desktop.getDesktop().browse(URI("https://www.google.com/maps/?q=$cleanCoordinates"))
    } catch (_: Exception) {
      // Silently ignore if browsing is not supported
    }
  }
}

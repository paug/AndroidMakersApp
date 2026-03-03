package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapItem
import platform.MapKit.MKPlacemark

class IosOpenMapUseCase : OpenMapUseCase {
  @OptIn(ExperimentalForeignApi::class)
  override operator fun invoke(context: PlatformContext, coordinates: String, name: String) {
    val coordinateArray = coordinates.split(",")
    if (coordinateArray.size == 2) {
      val latitude = coordinateArray[0].toDoubleOrNull()
      val longitude = coordinateArray[1].toDoubleOrNull()
      if (latitude != null && longitude != null) {
        val coordinate = CLLocationCoordinate2DMake(latitude, longitude)
        val placemark = MKPlacemark(coordinate, addressDictionary = null)
        val mapItem = MKMapItem(placemark)
        mapItem.name = name
        mapItem.openInMapsWithLaunchOptions(emptyMap<Any?, Any>())
      }
    }
  }
}

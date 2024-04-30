package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.NativePtr
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.objcPtr
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationDegrees
import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterStyle
import platform.MapKit.MKMapItem
import platform.MapKit.MKPlacemark

actual class OpenMapUseCase {
  @OptIn(ExperimentalForeignApi::class)
  actual operator fun invoke(platformContext: PlatformContext, coordinates: String, name: String) {
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

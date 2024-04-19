package fr.androidmakers.domain.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class UrlOpener {
  actual fun openUrl(url: String): Boolean {
    val urlObj = NSURL(string = url)
    return if (UIApplication.sharedApplication.canOpenURL(urlObj)) {
      val application = UIApplication.sharedApplication

      application.openURL(url = urlObj, options = emptyMap<Any?, Any>()) {
        if (!it) {
          println("Could not open $url")
        }
      }
      true
    } else {
      false
    }
  }
}


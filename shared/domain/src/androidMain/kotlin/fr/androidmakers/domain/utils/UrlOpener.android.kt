package fr.androidmakers.domain.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import fr.androidmakers.domain.PlatformContext

actual class UrlOpener {
  actual fun openUrl(platformContext: PlatformContext, url: String): Boolean {
    return try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
      platformContext.context.startActivity(intent)
      true
    } catch (anfe: ActivityNotFoundException) {
      anfe.printStackTrace()
      false
    }
  }
}

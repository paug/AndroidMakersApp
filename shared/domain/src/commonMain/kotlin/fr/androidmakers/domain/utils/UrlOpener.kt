package fr.androidmakers.domain.utils

import fr.androidmakers.domain.PlatformContext

expect class UrlOpener {
  fun openUrl(platformContext: PlatformContext, url: String): Boolean
}

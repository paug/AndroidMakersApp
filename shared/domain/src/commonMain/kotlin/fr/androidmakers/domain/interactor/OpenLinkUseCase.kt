package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.UrlOpener

class OpenLinkUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext, url: String) {
    urlOpener.openUrl(platformContext, url)
  }
}

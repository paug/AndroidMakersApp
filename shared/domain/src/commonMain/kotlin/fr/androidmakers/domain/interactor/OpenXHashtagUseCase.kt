package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenXHashtagUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext) {
    if (!urlOpener.openUrl(platformContext, Constants.Urls.xHashtagApp)) {
      urlOpener.openUrl(platformContext, Constants.Urls.xHashtagWeb)
    }
  }
}

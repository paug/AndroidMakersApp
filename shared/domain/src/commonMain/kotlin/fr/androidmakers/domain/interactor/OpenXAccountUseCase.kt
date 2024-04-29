package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenXAccountUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext) {
    if (!urlOpener.openUrl(platformContext, Constants.Urls.xAccountApp)) {
      urlOpener.openUrl(platformContext, Constants.Urls.xAccountWeb)
    }
  }
}

package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenYoutubeUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext) {
    // Try to open the application first
    if (!urlOpener.openUrl(platformContext, Constants.Urls.youtubeApp)) {
      urlOpener.openUrl(platformContext, Constants.Urls.youtubeWeb)
    }
  }
}

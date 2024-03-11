package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenYoutubeUseCase(
    private val urlOpener: UrlOpener
) {
  operator fun invoke() {
    // Try to open the application first
    if (!urlOpener.openUrl(Constants.Urls.youtubeApp)) {
      urlOpener.openUrl(Constants.Urls.youtubeWeb)
    }
  }
}

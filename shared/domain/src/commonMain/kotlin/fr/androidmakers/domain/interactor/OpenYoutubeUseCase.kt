package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenYoutubeUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    // Try to open the application first
    if (!urlOpener.openUrl(Constants.Urls.youtubeApp)) {
      urlOpener.openUrl(Constants.Urls.youtubeWeb)
    }
  }
}

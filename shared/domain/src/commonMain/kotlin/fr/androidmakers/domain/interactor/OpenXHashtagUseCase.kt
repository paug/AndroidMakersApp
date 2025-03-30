package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenXHashtagUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    if (!urlOpener.openUrl(Constants.Urls.xHashtagApp)) {
      urlOpener.openUrl(Constants.Urls.xHashtagWeb)
    }
  }
}

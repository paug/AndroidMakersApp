package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenXAccountUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    if (!urlOpener.openUrl(Constants.Urls.xAccountApp)) {
      urlOpener.openUrl(Constants.Urls.xAccountWeb)
    }
  }
}

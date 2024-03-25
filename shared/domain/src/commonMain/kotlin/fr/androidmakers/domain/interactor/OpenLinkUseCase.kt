package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.UrlOpener

class OpenLinkUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(url: String) {
    urlOpener.openUrl(url)
  }
}

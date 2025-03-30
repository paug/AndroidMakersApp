package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.UrlOpener

class OpenBlueskyAccountUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    urlOpener.openUrl("https://bsky.app/search?q=%23AMxDC25")
  }
}

package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.UrlOpener

class OpenBlueskyAccountUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext) {
    urlOpener.openUrl(platformContext, "https://bsky.app/search?q=%23AMxDC25")
  }
}

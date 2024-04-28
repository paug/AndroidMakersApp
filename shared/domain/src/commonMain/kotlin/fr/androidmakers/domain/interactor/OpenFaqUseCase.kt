package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenFaqUseCase(
  private val urlOpener: UrlOpener
) {
  operator fun invoke(platformContext: PlatformContext) =
    urlOpener.openUrl(platformContext, Constants.Urls.faq)
}

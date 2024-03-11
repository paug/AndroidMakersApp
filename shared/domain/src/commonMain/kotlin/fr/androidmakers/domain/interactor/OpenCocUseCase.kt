package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenCocUseCase(
    private val urlOpener: UrlOpener
) {
  operator fun invoke() = urlOpener.openUrl(Constants.Urls.coc)
}

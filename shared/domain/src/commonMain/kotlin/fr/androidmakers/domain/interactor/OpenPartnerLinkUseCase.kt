package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.utils.UrlOpener

class OpenPartnerLinkUseCase(
    private val urlOpener: UrlOpener
) {
  operator fun invoke(partner: Partner) {
    urlOpener.openUrl(partner.url)
  }
}

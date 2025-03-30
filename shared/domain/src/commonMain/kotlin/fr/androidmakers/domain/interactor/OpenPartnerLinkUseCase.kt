package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Partner
import fr.androidmakers.domain.utils.UrlOpener

class OpenPartnerLinkUseCase() {
  operator fun invoke(urlOpener: UrlOpener, partner: Partner) {
    urlOpener.openUrl(partner.url)
  }
}

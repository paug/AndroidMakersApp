package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.APPLY_FOR_APP_CLINIC_MAILTO_URL
import fr.androidmakers.domain.utils.UrlOpener

class ApplyForAppClinicUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    urlOpener.openUrl(APPLY_FOR_APP_CLINIC_MAILTO_URL)
  }
}

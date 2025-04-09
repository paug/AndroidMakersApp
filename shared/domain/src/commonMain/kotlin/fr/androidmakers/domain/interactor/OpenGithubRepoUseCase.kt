
package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.utils.Constants
import fr.androidmakers.domain.utils.UrlOpener

class OpenGithubRepoUseCase() {
  operator fun invoke(urlOpener: UrlOpener) {
    urlOpener.openUrl(Constants.Urls.githubRepo)
  }
}

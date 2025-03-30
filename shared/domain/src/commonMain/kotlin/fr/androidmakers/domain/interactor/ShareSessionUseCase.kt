package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

// TODO to be improved
interface ShareSessionUseCase {
  operator fun invoke(
    context: PlatformContext,
    session: Session,
    speakers: List<Speaker>,
    formattedDateAndRoom: String
  )
}

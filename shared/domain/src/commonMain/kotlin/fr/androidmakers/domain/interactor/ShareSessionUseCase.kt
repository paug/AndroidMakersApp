package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

// TODO to be improved
expect class ShareSessionUseCase {
  operator fun invoke(session: Session, speakers: List<Speaker>, formattedDateAndRoom: String)
}

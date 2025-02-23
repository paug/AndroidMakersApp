package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAgendaUseCase(
    private val sessionsRepository: SessionsRepository,
    private val speakersRepository: SpeakersRepository,
    private val roomsRepository: RoomsRepository,
) {
  operator fun invoke(): Flow<Result<Agenda>> {
    return combine(
        sessionsRepository.getSessions(),
        roomsRepository.getRooms(),
        speakersRepository.getSpeakers(),
    ) { sessionsResult, roomsResult, speakersResult ->

      val sessions = sessionsResult.getOrElse {
        it.printStackTrace()
        return@combine Result.failure(it)
      }
      val rooms = roomsResult.getOrElse {
        it.printStackTrace()
        return@combine Result.failure(it)
      }
      val speakers = speakersResult.getOrElse {
        it.printStackTrace()
        return@combine Result.failure(it)
      }

      Result.success(
          Agenda(
              sessions = sessions.associateBy { it.id },
              rooms = rooms.associateBy { it.id },
              speakers = speakers.associateBy { it.id }
          )
      )
    }
  }
}

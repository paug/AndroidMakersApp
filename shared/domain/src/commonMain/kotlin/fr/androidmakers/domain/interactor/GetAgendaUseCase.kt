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
    ) { sessions, rooms, speakers ->

      sessions.exceptionOrNull()?.let {
        it.printStackTrace()
        return@combine Result.failure(it)
      }
      rooms.exceptionOrNull()?.let {
        it.printStackTrace()
        return@combine Result.failure(it)
      }
      speakers.exceptionOrNull()?.let {
        it.printStackTrace()
        return@combine Result.failure(it)
      }

      Result.success(
          Agenda(
              sessions = sessions.getOrThrow().associateBy { it.id },
              rooms = rooms.getOrThrow().associateBy { it.id },
              speakers = speakers.getOrThrow().associateBy { it.id }
          )
      )
    }
  }
}

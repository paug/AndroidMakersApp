package fr.androidmakers.store.firebase

import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.model.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.time.ZoneOffset


class FirebaseStore : AndroidMakersStore {
  override fun getVenue(id: String): Flow<Result<Venue>> {
    return FirebaseSingleton.firestore.collection("venues")
        .document(id)
        .toFlow()
        .mapNotNull {
          Venue(
              address = it.get("address") as? String ?: "",
              name = it.get("name") as? String ?: "",
              description = it.get("description") as? String ?: "",
              descriptionFr = it.get("descriptionFr") as? String ?: "",
              coordinates = it.get("coordinates") as? String ?: "",
              imageUrl = it.get("imageUrl") as? String ?: "",
          )
        }.toResultFlow()
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    return FirebaseSingleton.firestore.collection("speakers")
        .document(id)
        .toFlow()
        .mapNotNull {
          it.toObject(Speaker::class.java)
        }.toResultFlow()
  }

  override fun getRoom(id: String): Flow<Result<Room>> {
    return getRooms().map {
      it.map { it.single { it.id == id } }
    }
  }

  override fun getRooms(): Flow<Result<List<Room>>> {
    return FirebaseSingleton.firestore.collection("rooms")
        .toFlow()
        .mapNotNull {
          it.documents.mapNotNull { it.toObject(Room::class.java)?.copy(id = it.id) }.plus(
              // A failsafe "all" room
              Room(
                  id = "all",
                  name = "Service",
              )
          )
        }.toResultFlow()
  }

  override fun getSession(id: String): Flow<Result<Session>> {
    return FirebaseSingleton.firestore.collection("sessions")
        .document(id)
        .toFlow()
        .mapNotNull {
          it.toObject(Session::class.java)?.copy(id = it.id)
        }
        .toResultFlow()
  }

  @OptIn(FlowPreview::class)
  override fun getPartners(): Flow<Result<List<Partner>>> {
    return FirebaseSingleton.firestore.collection("partners")
        .toFlow()
        .map {
          it.documents.map {
            val partner = it.toObject(Partner::class.java)!!
            FirebaseSingleton.firestore
                .collection("partners")
                .document(it.id)
                .collection("items")
                .toFlow()
                .map {
                  it.documents.map {
                    it.toObject(Logo::class.java)!!
                  }
                }.map {
                  partner.copy(
                      logos = it
                  )
                }

          }
        }.map {
          combine(it) {
            it.toList()
          }
        }
        .flattenConcat()
        .toResultFlow()
  }

  override fun getScheduleSlots(): Flow<Result<List<ScheduleSlot>>> {
    return FirebaseSingleton.firestore
        .collection("schedule")
        .toFlow()
        .map {
          convertResults(
              it.documents.map {
                it.id to it.data!!
              }.toMap()
          )!!
        }.toResultFlow()
  }

  override fun getSessions(): Flow<Result<List<Session>>> {
    return FirebaseSingleton.firestore.collection("sessions")
        .toFlow()
        .map { result ->
          result.documents.mapNotNull { it.toObject(Session::class.java)?.copy(id = it.id) }
        }.toResultFlow()
  }

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    return FirebaseSingleton.firestore.collection("speakers")
        .toFlow()
        .map { result ->
          result.documents.mapNotNull { it.toObject(Speaker::class.java)?.copy(id = it.id) }
        }.toResultFlow()
  }

  private fun <T> Flow<T>.toResultFlow(): Flow<Result<T>> = this.map {
    Result.success(it)
  }.catch {
    Result.failure<T>(it)
  }

  private fun convertResults(results: Map<String, Map<String, Any?>>): List<ScheduleSlot> {
    val list = mutableListOf<ScheduleSlot>()
    for (result in results) {
      val day = result.value
      val timeSlots = day.getAsListOfMaps("timeslots")

      timeSlots.forEachIndexed { timeSlotIndex, timeSlot ->
        val sessions = timeSlot.getAsListOfMaps("sessions")
        sessions.forEachIndexed { index, session ->
          val sessionId = session.getAsListOfStrings("items").firstOrNull()
          if (sessionId != null) {
            val startTime = timeSlot.getAsString("startTime")

            val extend = (session.get("extend") as Long?)?.toInt()?.minus(1) ?: 0
            val endTime = timeSlots[timeSlotIndex + extend].getAsString("endTime")
            val roomId = when (sessions.size) {
              1 -> "all"
              else -> index.toString()
            }

            list.add(
                ScheduleSlot(
                    startDate = getDate(result.key, startTime),
                    endDate = getDate(result.key, endTime),
                    roomId = roomId,
                    sessionId = sessionId
                )
            )
          }
        }
      }
    }

    return list.filter {
      it.sessionId != "no-op"
    }
  }

  /**
   * @param date the date as YYYY-MM-DD
   * @param time the time as HH:mm
   * @return a ISO86-01 String
   */
  private fun getDate(date: String, time: String): String {
    return LocalDateTime.parse("${date}T$time").atOffset(ZoneOffset.ofHours(2)).toInstant().toString()
  }
}


private fun <K> Map<K, *>.getAsListOfMaps(k: K): List<Map<String, *>> {
  @Suppress("UNCHECKED_CAST")
  return this.get(k) as List<Map<String, *>>
}

private fun <K> Map<K, *>.getAsListOfStrings(k: K): List<String> {
  @Suppress("UNCHECKED_CAST")
  return this.get(k) as List<String>
}

fun <K> Map<K, *>.getAsString(k: K): String {
  return this.get(k) as String
}

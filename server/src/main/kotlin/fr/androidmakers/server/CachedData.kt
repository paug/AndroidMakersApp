package fr.androidmakers.server

import fr.androidmakers.server.model.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.text.SimpleDateFormat
import java.util.*


object CachedData {
  private lateinit var _rooms: List<Room>
  private lateinit var _sessions: List<Session>
  private lateinit var _speakers: List<Speaker>

  private val json = Json {
    ignoreUnknownKeys = true
  }

  val rooms: List<Room>
    get() = _rooms
  val sessions: List<Session>
    get() = _sessions
  val speakers: List<Speaker>
    get() = _speakers

  @OptIn(ExperimentalSerializationApi::class)
  fun initialize() {
    _rooms = this.javaClass.classLoader.getResourceAsStream("rooms.json").use {
      json.decodeFromStream<JsonRoomData>(it)
    }.map {
      Room(
        id = it.key,
        capacity = it.value.capacity,
        name = it.value.name,
        level = it.value.level,
      )
    }

    val slots = this.javaClass.classLoader.getResourceAsStream("schedule.json").use {
      json.decodeFromStream<JsonSchedule>(it)
    }.toSlots()

    _sessions = this.javaClass.classLoader.getResourceAsStream("sessions.json").use {
      json.decodeFromStream<JsonSessionData>(it)
    }.mapNotNull { entry ->
      val slot = slots.firstOrNull {
        it.sessionId == entry.key
      }

      if (slot == null) {
        println("No slot found for session ${entry.key}")
        return@mapNotNull null
      }

      Session(
        id = entry.key,
        title = entry.value.title,
        complexity = entry.value.complexity,
        description = entry.value.description,
        feedback = entry.value.feedback,
        icon = entry.value.icon,
        language = entry.value.language,
        platformUrl = entry.value.platformUrl,
        slido = entry.value.slido,
        speakerIds = entry.value.speakers.toSet(),
        tags = entry.value.tags,
        startDate = slot.startDate,
        endDate = slot.endDate,
        roomId = slot.roomId
      )
    }

    _speakers = this.javaClass.classLoader.getResourceAsStream("speakers.json").use {
      json.decodeFromStream<JsonSpeakerData>(it)
    }.map {
      Speaker(
        id = it.key,
        name = it.value.name,
        bio = it.value.bio,
        shortBio = it.value.shortBio,
        photo = it.value.photo,
        photoUrl = it.value.photoUrl,
        company = it.value.company,
        companyLogo = it.value.companyLogo,
        country = it.value.country,
        featured = it.value.featured,
        order = it.value.order,
        socials = it.value.socials.map { jsonSocial ->
          Social(
            icon = jsonSocial.icon,
            name = jsonSocial.name,
            link = jsonSocial.link
          )
        }
      )
    }
  }
}

private class Slot(
  val endDate: String,
  val sessionId: String,
  val roomId: String,
  val startDate: String
)

private fun JsonSchedule.toSlots(): List<Slot> {
  val list = mutableListOf<Slot>()
  for (result in this) {
    val day = result.value ?: return emptyList()
    val timeSlots = day.timeslots

    timeSlots.forEachIndexed { timeSlotIndex, timeSlot ->
      val sessions = timeSlot.sessions
      sessions.forEachIndexed { index, session ->
        val sessionId = session.items.firstOrNull()
        if (sessionId != null) {
          val startTime = timeSlot.startTime

          val extend = (session.extend)?.minus(1) ?: 0
          val endTime = timeSlots[timeSlotIndex + extend].endTime
          val roomId = when (sessions.size) {
            1 -> "all"
            else -> index.toString()
          }

          list.add(
            Slot(
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
  val d = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse("$date $time") ?: error("Cannot parse $date")

  val tz = TimeZone.getTimeZone("UTC")
  val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
  df.timeZone = tz
  return df.format(d)
}
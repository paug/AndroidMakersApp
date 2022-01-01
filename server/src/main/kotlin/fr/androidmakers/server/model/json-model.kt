package fr.androidmakers.server.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonRoom(
  val name: String,
  val capacity: Int,
  val level: String
)

typealias JsonRoomData = Map<String, JsonRoom>

@Serializable
data class JsonSession(
  val title: String,
  val description: String,
  val language: String? = null,
  val complexity: String? = null,
  val speakers: List<String> = emptyList(),
  val tags: List<String> = emptyList(),
  val icon: String? = null,
  val platformUrl: String? = null,
  val feedback: String? = null,
  val slido: String? = null,
)

typealias JsonSessionData = Map<String, JsonSession>

@Serializable
data class JsonSpeaker(
  val order: Float? = null,
  val featured: Boolean = false,
  val name: String,
  val bio: String,
  val shortBio: String,
  val country: String?,
  val companyLogo: String? = null,
  val company: String? = null,
  val photo: String,
  val socials: List<JsonSocial>,
  val photoUrl: String
)

typealias JsonSpeakerData = Map<String, JsonSpeaker>

@Serializable
data class JsonSocial(
  val icon: String,
  val link: String,
  val name: String
)

typealias JsonSchedule = Map<String, JsonDay>

@Serializable
data class JsonDay(
  val timeslots: List<JsonTimeslot>,
  val tracks: List<JsonTrack>
)

@Serializable
data class JsonTimeslot(
  val startTime: String,
  val endTime: String,
  val sessions: List<JsonTimeslotSession>,
)

@Serializable
data class JsonTimeslotSession(
  val items: List<String>,
  val extend: Int? = null,
)

@Serializable
data class JsonTrack(
  val title: String,
  val infos: String,
)
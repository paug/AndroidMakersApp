package fr.androidmakers.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration

data class Session(
    val id: String = "",
    val complexity: Complexity? = null,
    val speakers: List<String> = listOf(),
    val description: String? = null,
    val language: String = "",
    val title: String = "",
    val tags: List<String> = listOf(),
    val videoURL: String = "",
    val slido: String? = null,
    val platformUrl: String? = null,
    //TODO unsure
    val slidesUrl: String? = null,

    // TODO move to instant to handle timezone
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val duration: Duration = endsAt.toInstant(TimeZone.UTC) - startsAt.toInstant(TimeZone.UTC),
    val roomId: String,
    val isServiceSession: Boolean,
)

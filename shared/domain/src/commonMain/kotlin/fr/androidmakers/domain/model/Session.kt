package fr.androidmakers.domain.model

import kotlinx.datetime.LocalDateTime
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
    val duration: Duration = Duration.ZERO,

    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val roomId: String,
    val isServiceSession: Boolean,
)

package fr.androidmakers.store.model

import kotlinx.datetime.LocalDateTime

data class Session(
    val id: String = "",
    val complexity: String = "",
    val speakers: List<String> = listOf(),
    val description: String? = null,
    val language: String = "",
    val title: String = "",
    val tags: List<String> = listOf(),
    val videoURL: String = "",
    val slido: String? = null,
    val platformUrl: String? = null,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val roomId: String,
)

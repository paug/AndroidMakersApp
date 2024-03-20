package fr.androidmakers.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

expect fun LocalDateTime.formatShortTime(): String
expect fun Instant.formatShortTime(): String

val eventTimeZone = TimeZone.of("Europe/Paris")

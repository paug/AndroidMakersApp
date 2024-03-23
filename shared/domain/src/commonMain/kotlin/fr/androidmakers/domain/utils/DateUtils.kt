package fr.androidmakers.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

expect fun LocalDateTime.formatShortTime(): String

expect fun LocalDate.formatMediumDate(): String

expect fun Instant.formatShortTime(): String

val eventTimeZone = TimeZone.of("Europe/Paris")

expect fun formatTimeInterval(startDate: LocalDateTime, endDate: LocalDateTime): String

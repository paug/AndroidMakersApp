package fr.androidmakers.store.model

import kotlinx.datetime.LocalDateTime

data class ScheduleSlot(
        val endDate: LocalDateTime,
        val sessionId: String = "",
        val roomId: String,
        val startDate: LocalDateTime,
)
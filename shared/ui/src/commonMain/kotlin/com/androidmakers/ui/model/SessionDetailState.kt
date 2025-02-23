package com.androidmakers.ui.model

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.utils.formatTimeInterval
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SessionDetailState(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val startTimestamp: Instant,
    val endTimestamp: Instant,
    val isBookmarked: Boolean,
) {
    fun formattedDateAndRoom(): String {
        val formattedDate = formatTimeInterval(
            startTimestamp.toLocalDateTime(TimeZone.currentSystemDefault()),
            endTimestamp.toLocalDateTime(TimeZone.currentSystemDefault())
        )
        return if (room.name.isNotEmpty()) {
            "$formattedDate, ${room.name}"
        } else {
            formattedDate
        }
    }
}

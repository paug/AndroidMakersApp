package com.androidmakers.ui.model

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import kotlinx.datetime.Instant

class SessionDetailState(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val startTimestamp: Instant,
    val endTimestamp: Instant,
    val isBookmarked: Boolean,
)

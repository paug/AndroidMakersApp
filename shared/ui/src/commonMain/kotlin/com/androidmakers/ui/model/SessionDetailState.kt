package com.androidmakers.ui.model

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

class SessionDetailState(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val isBookmarked: Boolean,
)

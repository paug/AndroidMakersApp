package fr.paug.androidmakers.wear.ui.main

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

data class SessionDetails(
    val session: Session,
    val speakers: List<Speaker>,
    val room: Room,
)

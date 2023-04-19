package fr.paug.androidmakers.ui.model

import kotlinx.datetime.Instant

class UISession(
    val id: String,
    val title: String,
    val startDate: Instant,
    val endDate: Instant,
    val language: String?,
    val room: String,
    val roomId: String,
    val speakers: List<Speaker>,
    val isServiceSession: Boolean,
) {
  class Speaker(val name: String)
}

class UIVenue(
    val imageUrl: String,
    val name: String,
    val address: String?,
    val coordinates: String?,
    val descriptionEn: String,
    val descriptionFr: String,
)
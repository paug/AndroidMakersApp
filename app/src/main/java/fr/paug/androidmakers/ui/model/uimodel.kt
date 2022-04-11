package fr.paug.androidmakers.ui.model

import java.time.Instant

class UISession(
    public val id: String,
    public val title: String,
    public val startDate: Instant,
    public val endDate: Instant,
    public val language: String?,
    public val room: String,
    public val roomId: String,
    public val speakers: List<Speaker>,
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
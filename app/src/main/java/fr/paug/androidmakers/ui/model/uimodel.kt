package fr.paug.androidmakers.ui.model

import java.time.Instant

class UiSession(
    public val title: String,
    public val startDate: Instant,
    public val endDate: Instant,
    public val complexity: String?,
    public val language: String?,
    public val room: String,
    public val speakers: List<Speaker>,
) {
  class Speaker(val name: String)
}
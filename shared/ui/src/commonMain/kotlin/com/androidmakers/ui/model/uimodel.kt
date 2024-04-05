package com.androidmakers.ui.model

import kotlinx.datetime.Instant

data class UISession(
    val id: String,
    val title: String,
    val startDate: Instant,
    val endDate: Instant,
    val language: String?,
    val room: String,
    val roomId: String,
    val speakers: List<Speaker>,
    val isServiceSession: Boolean,
    var isFavorite: Boolean,
) {
  data class Speaker(val name: String)
}

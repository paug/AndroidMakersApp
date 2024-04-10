package com.androidmakers.ui.model

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
    var isFavorite: Boolean,
    var isAppClinic: Boolean = false,
) {
  class Speaker(val name: String)
}

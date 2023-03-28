package fr.paug.androidmakers.ui.adapter

import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.util.EmojiUtils

data class ScheduleSession(
    var slot: ScheduleSlot,
    val title: String = "",
    val language: String = "",
    val speakers: List<Speaker> = mutableListOf()
) : Comparable<ScheduleSession> {

  val languageInEmoji: String get() = EmojiUtils.getLanguageInEmoji(language) ?: ""

  val sessionId get() = slot.sessionId
  val roomId get() = slot.roomId

  override fun compareTo(other: ScheduleSession): Int {
    return slot.startDate.compareTo(other.slot.startDate)
  }

}
package fr.paug.androidmakers.ui.adapter

import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.TimeUtils
import java.text.SimpleDateFormat

data class ScheduleSession(
    var slot: ScheduleSlot = ScheduleSlot(),
    val title: String = "",
    val language: String = "",
    val speakers: List<Speaker> = mutableListOf()
) : Comparable<ScheduleSession> {

    val languageInEmoji: String get() = EmojiUtils.getLanguageInEmoji(language) ?: ""

    val startTimestamp get() = TimeUtils.parseIso8601(slot.startDate).time
    val endTimestamp get() = TimeUtils.parseIso8601(slot.endDate).time
    val sessionId get() = slot.sessionId
    val roomId get() = slot.roomId

    override fun compareTo(other: ScheduleSession): Int {
        val slotDate = TimeUtils.parseIso8601(slot.startDate)
        val otherDate = TimeUtils.parseIso8601(other.slot.startDate)
        return slotDate.time.compareTo(otherDate.time)
    }

}
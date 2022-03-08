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

    val languageInEmoji: String get() = EmojiUtils.getLanguageInEmoji(language)

    val format = SimpleDateFormat(TimeUtils.dateFormat)

    val startTimestamp get() = format.parse(slot.startDate).time
    val endTimestamp get() = format.parse(slot.endDate).time
    val sessionId get() = slot.sessionId
    val roomId get() = slot.roomId

    override fun compareTo(other: ScheduleSession): Int {
        val slotDate = format.parse(slot.startDate)
        val otherDate = format.parse(other.slot.startDate)
        return slotDate.time.compareTo(otherDate.time)
    }

}
package fr.paug.androidmakers.ui.adapter

import fr.paug.androidmakers.model.ScheduleSlotKt
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.TimeUtils
import java.text.SimpleDateFormat

data class ScheduleSessionKt(
        var slot: ScheduleSlotKt,
        val title: String,
        val language: String
) : Comparable<ScheduleSessionKt> {

    val languageInEmoji: String get() = EmojiUtils.getLanguageInEmoji(language)

    val format = SimpleDateFormat(TimeUtils.dateFormat)

    val startTimestamp get() = format.parse(slot.startDate).time
    val endTimestamp get() = format.parse(slot.endDate).time
    val sessionId get() = slot.sessionId
    val roomId get() = slot.roomId

    override fun compareTo(other: ScheduleSessionKt): Int {
        val slotDate = format.parse(slot.startDate)
        val otherDate = format.parse(other.slot.startDate)
        return slotDate.time.compareTo(otherDate.time)
    }

}
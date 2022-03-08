package fr.paug.androidmakers.ui.adapter

import fr.paug.androidmakers.util.EmojiUtils.getLanguageInEmoji
import fr.paug.androidmakers.ui.adapter.RoomSchedule
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.model.ScheduleSlot
import fr.paug.androidmakers.util.EmojiUtils
import androidx.fragment.app.FragmentPagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.fragment.VenueConferenceFragment
import fr.paug.androidmakers.ui.fragment.VenueAfterPartyFragment
import fr.paug.androidmakers.ui.fragment.VenueFloorPlanFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Checkable
import fr.paug.androidmakers.ui.util.CheckableFloatingActionButton

class ScheduleSession(
    private val mScheduleSlot: ScheduleSlot,
    val title: String,
    val language: String
) : Comparable<ScheduleSession> {
    val startTimestamp: Long
        get() = mScheduleSlot.startDate
    val endTimestamp: Long
        get() = mScheduleSlot.endDate
    val languageInEmoji: String
        get() = getLanguageInEmoji(language)
    val roomId: Int
        get() = mScheduleSlot.room
    val sessionId: Int
        get() = mScheduleSlot.sessionId

    override fun toString(): String {
        return "ScheduleSession{" +
                "mScheduleSlot=" + mScheduleSlot +
                ", mTitle='" + title + '\'' +
                '}'
    }

    override fun compareTo(o: ScheduleSession): Int {
        return java.lang.Long.valueOf(mScheduleSlot.startDate).compareTo(o.mScheduleSlot.startDate)
    }
}
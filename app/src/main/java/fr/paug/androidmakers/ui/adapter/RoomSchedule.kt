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

class RoomSchedule(val roomId: Int, val title: String, val items: List<ScheduleSession>) :
    Comparable<RoomSchedule> {
    override fun compareTo(o: RoomSchedule): Int {
        return roomId - o.roomId
    }

    override fun toString(): String {
        return "RoomSchedule{" +
                "mRoomId=" + roomId +
                ", mTitle='" + title + '\'' +
                ", mItems=" + items +
                '}'
    }
}
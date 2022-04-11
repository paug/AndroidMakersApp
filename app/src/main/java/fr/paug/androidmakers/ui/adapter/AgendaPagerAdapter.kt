package fr.paug.androidmakers.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.util.forEach
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.PagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.activity.SessionDetailActivity
import fr.paug.androidmakers.ui.components.AgendaColumn
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.TimeUtils
import java.time.OffsetDateTime
import java.util.*

class AgendaPagerAdapter(private val mAgenda: List<DaySchedule>, private val activity: Activity) : PagerAdapter() {
    private val mAgendaViews = SparseArray<View>()

    private var sessionFilterList: List<SessionFilter> = ArrayList()

    fun refreshSessionsSelected() {
    }

    private fun addSeparators(context: Context, sessions: List<UISession>): Map<String, List<UISession>> {
        return sessions.map {
            TimeUtils.formatShortTime(context, Date(it.startDate.toEpochMilli())) to it
        }
            .groupBy(
                keySelector =  { it.first }
            ) {
                it.second
            }
    }

    fun getRoomTitle(scheduleSession: ScheduleSession, daySchedule: DaySchedule): String {
        var roomTitle = ""
        for (roomSchedule in daySchedule.roomSchedules) {
            if (roomSchedule.roomId == scheduleSession.roomId) {
                roomTitle = roomSchedule.title
            }
        }
        return roomTitle
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = ComposeView(collection.context)

        updateSessions(view, position)

        collection.addView(view)
        mAgendaViews.put(position, view)
        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
        mAgendaViews.remove(position)
    }

    override fun getCount(): Int {
        return mAgenda.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getItems(position).title
    }

    private fun getItems(position: Int): DaySchedule {
        return mAgenda[position]
    }

    private fun List<ScheduleSession>.filterSessions(filterList: List<SessionFilter>): List<ScheduleSession> {
        val filteredSessions = mutableListOf<ScheduleSession>()

        if (sessionFilterList.isEmpty()) {
            filteredSessions.addAll(this)
        } else {
            for (item in this) {
                for (sessionFilter in sessionFilterList) {
                    val matched = when (sessionFilter.type) {
                        SessionFilter.FilterType.BOOKMARK -> {
                            SessionSelector.isSelected(item.sessionId)
                        }
                        SessionFilter.FilterType.LANGUAGE -> {
                            sessionFilter.value == item.language
                        }
                        SessionFilter.FilterType.ROOM -> {
                            sessionFilter.value == item.roomId
                        }
                    }

                    if (matched) {
                        filteredSessions.add(item)
                    }
                }
            }
        }

        return filteredSessions
    }

    private fun updateSessions(composeView: ComposeView, position: Int) {
        val daySchedule = getItems(position)
        val items = daySchedule.roomSchedules.flatMap { it.scheduleSessions }
            .filterSessions(sessionFilterList)
            .sorted()
            .map { item ->
            UISession(
                id = item.sessionId,
                title = item.title,
                language = item.language,
                startDate = OffsetDateTime.parse(item.slot.startDate).toInstant(),
                endDate = OffsetDateTime.parse(item.slot.endDate).toInstant(),
                room = getRoomTitle(item, daySchedule),
                roomId = item.roomId,
                speakers = item.speakers.map {
                    UISession.Speaker(it.name ?: "")
                },
            )
        }

        val grouped = addSeparators(
            composeView.context,
            items,
        )
        composeView.setContent {
            AndroidMakersTheme {
                AgendaColumn(grouped) {
                    SessionDetailActivity.startActivity(activity, it)
                }
            }
        }
    }

    fun setSessionFilterList(sessionFilterList: List<SessionFilter>) {
        this.sessionFilterList = sessionFilterList

        mAgendaViews.forEach { position, view ->
            updateSessions(view as ComposeView, position)
        }
    }
}
package fr.paug.androidmakers.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.PagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.activity.SessionDetailActivity
import fr.paug.androidmakers.ui.components.AgendaColumn
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.ui.util.SessionFilter
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

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = ComposeView(collection.context)

        val daySchedule = getItems(position)
        val items = daySchedule.roomSchedules.flatMap { it.scheduleSessions }.sorted().map { item ->
            UISession(
                id = item.sessionId,
                title = item.title,
                language = item.language,
                startDate = OffsetDateTime.parse(item.slot.startDate).toInstant(),
                endDate = OffsetDateTime.parse(item.slot.endDate).toInstant(),
                room = ScheduleDayAdapter.getRoomTitle(item, daySchedule),
                roomId = item.roomId,
                speakers = item.speakers.map {
                    UISession.Speaker(it.name ?: "")
                },
            )
        }

        view.setContent {
            AndroidMakersTheme {
                AgendaColumn(addSeparators(collection.context, items)) {
                    SessionDetailActivity.startActivity(activity, it)
                }
            }
        }

//        val adapter = ScheduleDayAdapter(activity,
//                true,
//                clickListener = { scheduleSession ->
//                    SessionDetailActivity.startActivity(activity, scheduleSession)
//                })
        //adapter.setSessionFilterList(sessionFilterList)

        //moveToCurrentTimeSlot(recyclerView, true, adapter)

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

    private fun moveToCurrentTimeSlot(recyclerView: RecyclerView, animate: Boolean, adapter: ScheduleDayAdapter) {
        val now = System.currentTimeMillis() // current time
        val position = adapter.findTimeHeaderPositionForTime(now)

        val smoothScroller = object : LinearSmoothScroller(activity) {
            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }
        }

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?

        if (position >= 0) {
            if (animate) {
                smoothScroller.targetPosition = position
                layoutManager!!.startSmoothScroll(smoothScroller)
            } else {
                layoutManager!!.scrollToPositionWithOffset(position, this.activity.resources.getDimensionPixelSize(R.dimen.default_padding))
            }
        }
    }

    fun setSessionFilterList(sessionFilterList: List<SessionFilter>) {
        this.sessionFilterList = sessionFilterList
    }
}
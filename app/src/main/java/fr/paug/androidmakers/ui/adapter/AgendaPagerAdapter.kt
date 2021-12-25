package fr.paug.androidmakers.ui.adapter

import android.app.Activity
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.PagerAdapter
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.activity.SessionDetailActivity
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.util.sticky_headers.StickyHeadersLinearLayoutManager
import java.util.*

class AgendaPagerAdapter(private val mAgenda: List<DaySchedule>, private val activity: Activity) : PagerAdapter() {
    private val mAgendaViews = SparseArray<View>()

    private var sessionFilterList: List<SessionFilter> = ArrayList()
    private val activeAdapterSet = HashSet<ScheduleDayAdapter>()

    fun refreshSessionsSelected() {
        for (adapter in activeAdapterSet) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(collection.context).inflate(R.layout.schedule_single_day_list, null, false)

        val recyclerView: RecyclerView
        recyclerView = view.findViewById(android.R.id.list)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.layoutManager = StickyHeadersLinearLayoutManager<ScheduleDayAdapter>(activity)

        val adapter = ScheduleDayAdapter(activity,
                getItems(position),
                true,
                clickListener = { scheduleSession ->
                    SessionDetailActivity.startActivity(activity, scheduleSession)
                })
        //adapter.setSessionFilterList(sessionFilterList)

        recyclerView.adapter = adapter
        activeAdapterSet.add(adapter)
        moveToCurrentTimeSlot(recyclerView, true, adapter)

        collection.addView(view)
        mAgendaViews.put(position, view)
        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
        val recyclerView = view.findViewById<RecyclerView>(android.R.id.list)
        activeAdapterSet.remove(recyclerView.adapter)
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
        for (adapter in activeAdapterSet) {
            adapter.setSessionFilterList(sessionFilterList)
        }
    }
}
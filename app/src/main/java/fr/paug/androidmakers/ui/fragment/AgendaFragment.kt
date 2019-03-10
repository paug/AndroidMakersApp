package fr.paug.androidmakers.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.SparseArray
import android.view.*
import android.view.MenuItem.SHOW_AS_ACTION_ALWAYS
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import fr.paug.androidmakers.R
import fr.paug.androidmakers.manager.AgendaRepository
import fr.paug.androidmakers.model.ScheduleSlot
import fr.paug.androidmakers.service.SessionAlarmService
import fr.paug.androidmakers.ui.adapter.AgendaPagerAdapter
import fr.paug.androidmakers.ui.adapter.DaySchedule
import fr.paug.androidmakers.ui.adapter.RoomSchedule
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.ui.util.SessionFilter.FilterType.*
import fr.paug.androidmakers.util.EmojiUtils
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.util.*

class AgendaFragment : Fragment() {

    private var mProgressView: View? = null
    private var mEmptyView: View? = null
    private var mViewPager: ViewPager? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mFiltersView: ViewGroup? = null

    private val allSessionFilters = ArrayList<SessionFilter>()
    private val allCheckBoxes = ArrayList<CheckBox>()

    private val mCheckBoxOnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> applyFilters() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keeps this Fragment alive during configuration changes
        retainInstance = true

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agenda, container, false)
        mViewPager = view.findViewById(R.id.viewpager)
        mProgressView = view.findViewById(R.id.progressbar)
        mEmptyView = view.findViewById(R.id.empty_view)
        mFiltersView = view.findViewById(R.id.filters)
        mDrawerLayout = view as DrawerLayout

        // auto dismiss loading
        Handler().postDelayed(RefreshRunnable(this), 3000)

        //AgendaRepository.getInstance().load(AgendaLoadListener(this))
        // TODO replace with cloud firestore code

        initFilters()

        return view
    }

    private fun applyFilters() {
        val adapter = mViewPager!!.adapter
        if (adapter is AgendaPagerAdapter) {
            val sessionFilterList = ArrayList<SessionFilter>()

            for (i in allCheckBoxes.indices) {
                if (allCheckBoxes[i].isChecked) {
                    sessionFilterList.add(allSessionFilters[i])
                }
            }
            adapter.setSessionFilterList(sessionFilterList)
        }
    }

    private fun initFilters() {
        mFiltersView!!.setOnTouchListener { v, event ->
            // a dummy touch listener that makes sure we don't click through the filter list
            true
        }
        addFilterHeader(R.string.filter)
        addFilter(SessionFilter(BOOKMARK, null), null)

        addFilterHeader(R.string.language)
        addFilter(SessionFilter(LANGUAGE, "fr"), null)
        addFilter(SessionFilter(LANGUAGE, "en"), null)

        AgendaRepository.getInstance().load(object : AgendaRepository.OnLoadListener {
            override fun onAgendaLoaded() {
                addFilterHeader(R.string.rooms)
                val rooms = AgendaRepository.getInstance().allRooms
                for (i in 0 until rooms.size()) {
                    val key = rooms.keyAt(i)
                    val roomName = rooms.get(key).name

                    if (!TextUtils.isEmpty(roomName)) {
                        addFilter(SessionFilter(ROOM, key), roomName)
                    }
                }
                AgendaRepository.getInstance().removeListener(this)
            }
        })

    }

    private fun addFilter(sessionFilter: SessionFilter, roomName: String?) {
        val view = LayoutInflater.from(activity).inflate(R.layout.filter_item, mDrawerLayout, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        checkBox.setOnCheckedChangeListener(mCheckBoxOnCheckedChangeListener)
        view.setOnClickListener { checkBox.isChecked = !checkBox.isChecked }
        mFiltersView!!.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val context = mFiltersView!!.context

        allCheckBoxes.add(checkBox)
        allSessionFilters.add(sessionFilter)

        var name: String? = ""

        val bookmark = view.findViewById<View>(R.id.bookmark)
        val flag = view.findViewById<TextView>(R.id.flag)

        bookmark.visibility = View.GONE
        flag.visibility = View.GONE

        when (sessionFilter.type) {
            BOOKMARK -> {
                name = context.getString(R.string.bookmarked)
                bookmark.visibility = View.VISIBLE
            }
            LANGUAGE -> {
                val nameResId = if ("fr" == sessionFilter.value) R.string.french else R.string.english
                name = context.getString(nameResId)

                flag.text = EmojiUtils.getLanguageInEmoji(sessionFilter.value as String)
                flag.visibility = View.VISIBLE
            }
            ROOM -> {
                bookmark.visibility = View.INVISIBLE
                name = roomName
            }
        }

        (view.findViewById<View>(R.id.name) as TextView).text = name
    }

    private fun addFilterHeader(@StringRes titleResId: Int) {
        val view = LayoutInflater.from(activity).inflate(R.layout.filter_header, mDrawerLayout, false)
        (view as TextView).setText(titleResId)
        mFiltersView!!.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu!!.add(activity!!.getString(R.string.filter))
        menuItem.setIcon(R.drawable.ic_filter_list_white_24dp)
        menuItem.setShowAsAction(SHOW_AS_ACTION_ALWAYS)
        menuItem.setOnMenuItemClickListener {
            if (mDrawerLayout!!.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout!!.closeDrawer(GravityCompat.END)
            } else {
                mDrawerLayout!!.openDrawer(GravityCompat.END)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = mViewPager!!.adapter
        if (adapter is AgendaPagerAdapter) {
            adapter.refreshSessionsSelected()
        }
    }

    private fun onAgendaLoaded() {
        val itemByDayOfTheYear = SparseArray<DaySchedule>()

        val calendar = Calendar.getInstance()
        val scheduleSlots = AgendaRepository.getInstance().scheduleSlots
        for (scheduleSlot in scheduleSlots) {
            val agendaScheduleSessions = getAgendaItems(
                    itemByDayOfTheYear, calendar, scheduleSlot)
            agendaScheduleSessions.add(ScheduleSession(scheduleSlot, getTitle(scheduleSlot.sessionId), getLanguage(scheduleSlot.sessionId)))
        }

        val days = getItemsOrdered(itemByDayOfTheYear)

        val adapter = AgendaPagerAdapter(days, activity)
        mViewPager!!.adapter = adapter
        applyFilters()

        val indexOfToday = getTodayIndex(days)
        if (indexOfToday > 0) {
            mViewPager!!.setCurrentItem(indexOfToday, true)
        }
        refreshViewsDisplay()
    }

    private fun refreshViewsDisplay() {
        mProgressView!!.visibility = View.GONE
        val adapter = mViewPager!!.adapter
        if (adapter == null || adapter.count == 0) {
            mEmptyView!!.visibility = View.VISIBLE
            mViewPager!!.visibility = View.GONE
        } else {
            mEmptyView!!.visibility = View.GONE
            mViewPager!!.visibility = View.VISIBLE
        }
    }

    private fun getTodayIndex(items: List<DaySchedule>?): Int {
        if (items == null || items.size < 2) {
            return -1
        }
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)
        for (i in 1 until items.size) {
            val agendaDaySchedule = items[i]
            val roomSchedules = agendaDaySchedule.roomSchedules
            if (!roomSchedules.isEmpty()) {
                val scheduleSessionList = roomSchedules[0].items
                if (!scheduleSessionList.isEmpty()) {
                    val scheduleSession = scheduleSessionList[0]
                    calendar.timeInMillis = scheduleSession.startTimestamp
                    if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.DAY_OF_YEAR) == dayOfYear) {
                        return i
                    }
                }
            }
        }
        return -1
    }

    private fun getAgendaItems(itemByDayOfTheYear: SparseArray<DaySchedule>,
                               calendar: Calendar, scheduleSlot: ScheduleSlot): MutableList<ScheduleSession> {
        val roomSchedules = getRoomScheduleForDay(itemByDayOfTheYear, calendar, scheduleSlot)
        var roomScheduleForThis: RoomSchedule? = null
        for (roomSchedule in roomSchedules) {
            if (roomSchedule.roomId == scheduleSlot.room) {
                roomScheduleForThis = roomSchedule
                break
            }
        }
        if (roomScheduleForThis == null) {
            val agendaScheduleSessions = ArrayList<ScheduleSession>()
            val room = AgendaRepository.getInstance().getRoom(scheduleSlot.room)
            val titleRoom = room?.name
            roomScheduleForThis = RoomSchedule(
                    scheduleSlot.room, titleRoom, agendaScheduleSessions)
            roomSchedules.add(roomScheduleForThis)
            Collections.sort(roomSchedules)
            return agendaScheduleSessions
        } else {
            return roomScheduleForThis.items
        }
    }

    private fun getRoomScheduleForDay(
            itemByDayOfTheYear: SparseArray<DaySchedule>,
            calendar: Calendar, scheduleSlot: ScheduleSlot): MutableList<RoomSchedule> {
        calendar.timeInMillis = scheduleSlot.startDate
        val dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000
        var daySchedule: DaySchedule? = itemByDayOfTheYear.get(dayIndex)
        if (daySchedule == null) {
            val roomSchedule = ArrayList<RoomSchedule>()
            val title = DateFormat.getDateInstance().format(calendar.time)
            daySchedule = DaySchedule(title, roomSchedule)
            itemByDayOfTheYear.put(dayIndex, daySchedule)
            return roomSchedule
        } else {
            return daySchedule.roomSchedules
        }
    }

    private fun getItemsOrdered(
            itemByDayOfTheYear: SparseArray<DaySchedule>): List<DaySchedule> {
        val size = itemByDayOfTheYear.size()
        val keysSorted = IntArray(size)
        for (i in 0 until size) {
            keysSorted[i] = itemByDayOfTheYear.keyAt(i)
        }
        Arrays.sort(keysSorted)
        val items = ArrayList<DaySchedule>(size)
        for (key in keysSorted) {
            items.add(itemByDayOfTheYear.get(key))
        }
        return items
    }

    private fun getTitle(sessionId: Int): String {
        val session = AgendaRepository.getInstance().getSession(sessionId)
        return if (session == null) "?" else session.title
    }

    private fun getLanguage(sessionId: Int): String {
        val session = AgendaRepository.getInstance().getSession(sessionId)
        return if (session == null) "?" else session.language
    }

    private class RefreshRunnable constructor(agendaFragment: AgendaFragment) : Runnable {
        private val mAgendaActivity: WeakReference<AgendaFragment>

        init {
            mAgendaActivity = WeakReference(agendaFragment)
        }

        override fun run() {
            val agendaFragment = mAgendaActivity.get()
            agendaFragment?.refreshViewsDisplay()
        }
    }

    private class AgendaLoadListener constructor(agendaFragment: AgendaFragment) : AgendaRepository.OnLoadListener {
        private val reference: WeakReference<AgendaFragment>

        init {
            reference = WeakReference(agendaFragment)
        }

        override fun onAgendaLoaded() {
            val agendaFragment = reference.get() ?: return
            agendaFragment.onAgendaLoaded()

            val fragment = reference.get()
            if (fragment != null) {
                // reschedule all starred blocks in case one session start or stop time has changed
                val ctx = fragment.context
                val scheduleIntent = Intent(SessionAlarmService.ACTION_SCHEDULE_ALL_STARRED_BLOCKS)
                SessionAlarmService.enqueueWork(ctx, scheduleIntent)
            }
        }
    }

}
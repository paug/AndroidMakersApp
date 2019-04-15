package fr.paug.androidmakers.ui.fragment

import android.content.Intent
import android.os.Bundle
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
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.model.RoomKt
import fr.paug.androidmakers.model.ScheduleSlotKt
import fr.paug.androidmakers.model.SessionKt
import fr.paug.androidmakers.model.SpeakerKt
import fr.paug.androidmakers.service.SessionAlarmService
import fr.paug.androidmakers.ui.adapter.AgendaPagerAdapter
import fr.paug.androidmakers.ui.adapter.DayScheduleKt
import fr.paug.androidmakers.ui.adapter.RoomScheduleKt
import fr.paug.androidmakers.ui.adapter.ScheduleSessionKt
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.ui.util.SessionFilter.FilterType.*
import fr.paug.androidmakers.util.EmojiUtils
import fr.paug.androidmakers.util.TimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AgendaFragment : Fragment() {

    private var mProgressView: View? = null
    private var mEmptyView: View? = null
    private var mViewPager: ViewPager? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mFiltersView: ViewGroup? = null

    private val allSessionFilters = ArrayList<SessionFilter>()
    private val allCheckBoxes = ArrayList<CheckBox>()

    private val mCheckBoxOnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> applyFilters() }

    var allSessions = HashMap<String, SessionKt>()
    var allSlots = listOf<ScheduleSlotKt>()
    var allRooms = listOf<RoomKt>()
    var allSpeakers = HashMap<String, SpeakerKt>()

    //region Fragment Implementation
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

        // Load Sessions, slots and rooms
        loadAgenda()

        return view
    }

    override fun onResume() {
        super.onResume()

        val adapter = mViewPager!!.adapter
        if (adapter is AgendaPagerAdapter) {
            adapter.refreshSessionsSelected()
        }
    }
    //endregion

    private fun refreshViewsDisplay() {
        mProgressView?.visibility = View.GONE
        val adapter = mViewPager!!.adapter
        if (adapter == null || adapter.count == 0) {
            mEmptyView?.visibility = View.VISIBLE
            mViewPager?.visibility = View.GONE
        } else {
            mEmptyView?.visibility = View.GONE
            mViewPager?.visibility = View.VISIBLE
        }
    }

    //region Schedule
    private fun loadAgenda() {
        AndroidMakersStore().getSessions { sessions ->
            allSessions = sessions
            AndroidMakersStore().getSlots { slots ->
                allSlots = slots
                AndroidMakersStore().getRooms { rooms ->
                    allRooms = rooms
                    AndroidMakersStore().getSpeakers { speakers ->
                        allSpeakers = speakers
                        onAgendaLoaded()
                    }
                }
            }
        }
    }

    private fun onAgendaLoaded() {
        val itemByDayOfTheYear = SparseArray<DayScheduleKt>()

        val calendar = Calendar.getInstance()
        val scheduleSlots = allSlots
        for (scheduleSlot in scheduleSlots) {
            val agendaScheduleSessions = getAgendaItems(itemByDayOfTheYear, calendar, scheduleSlot)
            agendaScheduleSessions.add(ScheduleSessionKt(scheduleSlot,
                    getTitle(scheduleSlot.sessionId),
                    getLanguage(scheduleSlot.sessionId),
                    getSpeakers(scheduleSlot.sessionId)))
        }

        val days = getItemsOrdered(itemByDayOfTheYear)

        activity?.let {
            initFilters()
            val adapter = AgendaPagerAdapter(days, it)
            mViewPager?.adapter = adapter
            //applyFilters()

            val indexOfToday = getTodayIndex(days)
            if (indexOfToday > 0) {
                mViewPager!!.setCurrentItem(indexOfToday, true)
            }
            refreshViewsDisplay()
            //rescheduleStarredBlocks()
        }
    }

    private fun getTodayIndex(items: List<DayScheduleKt>?): Int {
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
                val scheduleSessionList = roomSchedules[0].scheduleSessions
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

    private fun getAgendaItems(itemByDayOfTheYear: SparseArray<DayScheduleKt>,
                               calendar: Calendar,
                               scheduleSlot: ScheduleSlotKt): ArrayList<ScheduleSessionKt> {
        val roomSchedules = getRoomScheduleForDay(itemByDayOfTheYear, calendar, scheduleSlot)
        var roomScheduleForThis: RoomScheduleKt? = null
        for (roomSchedule in roomSchedules) {
            if (roomSchedule.roomId == scheduleSlot.roomId) {
                roomScheduleForThis = roomSchedule
                break
            }
        }
        if (roomScheduleForThis == null) {
            val agendaScheduleSessions = ArrayList<ScheduleSessionKt>()
            val room = allRooms.filter { it.roomId == scheduleSlot.roomId }.first()
            val titleRoom = room.roomName
            roomScheduleForThis = RoomScheduleKt(scheduleSlot.roomId, titleRoom, agendaScheduleSessions)
            roomSchedules.add(roomScheduleForThis)
            roomSchedules.sort()
            return agendaScheduleSessions
        } else {
            return roomScheduleForThis.scheduleSessions
        }
    }

    private fun getRoomScheduleForDay(itemByDayOfTheYear: SparseArray<DayScheduleKt>,
                                      calendar: Calendar,
                                      scheduleSlot: ScheduleSlotKt): MutableList<RoomScheduleKt> {
        val format = SimpleDateFormat(TimeUtils.dateFormat)
        val date = format.parse(scheduleSlot.startDate)

        calendar.timeInMillis = date.time
        val dayIndex = calendar.get(Calendar.DAY_OF_YEAR) + calendar.get(Calendar.YEAR) * 1000
        var daySchedule: DayScheduleKt? = itemByDayOfTheYear.get(dayIndex)
        if (daySchedule == null) {
            val roomSchedule = ArrayList<RoomScheduleKt>()
            val title = DateFormat.getDateInstance().format(calendar.time)
            daySchedule = DayScheduleKt(title, roomSchedule)
            itemByDayOfTheYear.put(dayIndex, daySchedule)
            return roomSchedule
        } else {
            return daySchedule.roomSchedules
        }
    }

    private fun getItemsOrdered(itemByDayOfTheYear: SparseArray<DayScheduleKt>): List<DayScheduleKt> {
        val size = itemByDayOfTheYear.size()
        val keysSorted = IntArray(size)
        for (i in 0 until size) {
            keysSorted[i] = itemByDayOfTheYear.keyAt(i)
        }
        Arrays.sort(keysSorted)
        val items = ArrayList<DayScheduleKt>(size)
        for (key in keysSorted) {
            items.add(itemByDayOfTheYear.get(key))
        }
        return items
    }

    private fun getTitle(sessionId: String): String {
        val session = allSessions[sessionId]
        return session!!.title
    }

    private fun getLanguage(sessionId: String): String {
        val session = allSessions[sessionId]
        return session!!.language
    }

    private fun getSpeakers(sessionId: String): ArrayList<SpeakerKt> {
        val speakers = arrayListOf<SpeakerKt>()
        val session = allSessions[sessionId]
        if (session?.speakers?.isNotEmpty() == true) {
            session.speakers.forEachIndexed { index, speakerId ->
                val speakerOfSession = allSpeakers[speakerId]
                speakers.add(speakerOfSession!!)
            }
        }
        return speakers
    }
    //endregion

    //region Filters
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
        mFiltersView?.setOnTouchListener { v, event ->
            // a dummy touch listener that makes sure we don't click through the filter list
            true
        }
        addFilterHeader(R.string.filter)
        addFilter(SessionFilter(BOOKMARK, ""), null)

        addFilterHeader(R.string.language)
        addFilter(SessionFilter(LANGUAGE, "French"), null)
        addFilter(SessionFilter(LANGUAGE, "English"), null)

        AndroidMakersStore().getRooms { rooms ->
            addFilterHeader(R.string.rooms)
            for (room in rooms) {
                if (!TextUtils.isEmpty(room.roomName)) {
                    addFilter(SessionFilter(ROOM, room.roomId), room.roomName)
                }
            }
        }
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
                val nameResId = if ("French" == sessionFilter.value) R.string.french else R.string.english
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
        mFiltersView?.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu?.add(activity!!.getString(R.string.filter))
        menuItem?.setIcon(R.drawable.ic_filter_list_white_24dp)
        menuItem?.setShowAsAction(SHOW_AS_ACTION_ALWAYS)
        menuItem?.setOnMenuItemClickListener {
            if (mDrawerLayout!!.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout!!.closeDrawer(GravityCompat.END)
            } else {
                mDrawerLayout!!.openDrawer(GravityCompat.END)
            }
            true
        }
    }
    //endregion

    fun rescheduleStarredBlocks() {
        // reschedule all starred blocks in case one session start or stop time has changed
        val scheduleIntent = Intent(SessionAlarmService.ACTION_SCHEDULE_ALL_STARRED_BLOCKS)
        SessionAlarmService.enqueueWork(context!!, scheduleIntent)
    }
}
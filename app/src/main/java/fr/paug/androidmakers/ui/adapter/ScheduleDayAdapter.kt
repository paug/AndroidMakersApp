package fr.paug.androidmakers.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.emoji.text.EmojiCompat
import androidx.recyclerview.widget.RecyclerView
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AgendaRow
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.util.ScheduleSessionHelper
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.TimeUtils
import fr.paug.androidmakers.util.sticky_headers.StickyHeaders
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

class ScheduleDayAdapter//region Constructor
internal constructor(private val context: Context,
                     private val daySchedule: DaySchedule,
                     private val mShowTimeSeparators: Boolean,
                     val clickListener: (ScheduleSession) -> Unit)
  : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaders, StickyHeaders.ViewSetup {

  private val mItems = ArrayList<Any>()
  private val mSessions = ArrayList<ScheduleSession>()
  private val stuckHeaderElevation: Float = context.resources.getDimension(R.dimen.card_elevation)
  private val sessionFilterList = ArrayList<SessionFilter>()

  init {
    val sessions = ArrayList<ScheduleSession>()
    for (roomSchedule in daySchedule.roomSchedules) {
      sessions.addAll(roomSchedule.scheduleSessions)
    }

    sessions.sort()
    setScheduleSessionList(sessions)
  }

  private fun setScheduleSessionList(sessions: List<ScheduleSession>) {
    this.mSessions.clear()
    this.mSessions.addAll(sessions)
    update()
  }

  fun setSessionFilterList(sessionFilterList: List<SessionFilter>) {
    this.sessionFilterList.clear()
    this.sessionFilterList.addAll(sessionFilterList)
    update()
  }

  //endregion

  //region RecyclerView Override
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      ITEM_TYPE_SESSION -> {
        object : RecyclerView.ViewHolder(ComposeView(parent.context)) {}
      }
      ITEM_TYPE_TIME_HEADER -> TimeSeparatorViewHolder(
          LayoutInflater.from(parent.context)
              .inflate(R.layout.schedule_item_time_separator, parent, false))
      else -> TimeSeparatorViewHolder(
          LayoutInflater.from(parent.context)
              .inflate(R.layout.schedule_item_time_separator, parent, false))
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = mItems[position]
    when (holder.itemViewType) {
      ITEM_TYPE_SESSION -> {
        check(item is ScheduleSession)
        (holder.itemView as ComposeView).setContent {
          AndroidMakersTheme {
            AgendaRow(
                uiSession = UISession(
                    id = item.sessionId,
                    title = item.title,
                    language = item.language,
                    startDate = OffsetDateTime.parse(item.slot.startDate).toInstant(),
                    endDate = OffsetDateTime.parse(item.slot.endDate).toInstant(),
                    room = getRoomTitle(item, daySchedule),
                    speakers = item.speakers.map {
                      UISession.Speaker(it.name ?: "")
                    },
                )
            )
          }
        }
      }
      ITEM_TYPE_TIME_HEADER -> (holder as TimeSeparatorViewHolder).bind(item as TimeSeparatorItem)
      else -> (holder as TimeSeparatorViewHolder).bind(item as TimeSeparatorItem)
    }
  }

  override fun getItemCount(): Int {
    return mItems.size
  }

  override fun getItemId(position: Int): Long {
    val item = mItems[position]
    if (item is ScheduleSession) {
      return generateIdForScheduleItem(item)
    } else if (item is TimeSeparatorItem) {
      return item.hashCode().toLong()
    }
    return position.toLong()
  }

  override fun getItemViewType(position: Int): Int {
    val item = mItems[position]
    if (item is ScheduleSession) {
      //if (((ScheduleSessionKt) item).type == ScheduleSessionKt.BREAK) {
      //    return ITEM_TYPE_BREAK;
      //}
      return ITEM_TYPE_SESSION
    } else if (item is TimeSeparatorItem) {
      return ITEM_TYPE_TIME_HEADER
    }
    return RecyclerView.INVALID_TYPE
  }

  fun findTimeHeaderPositionForTime(time: Long): Int {
    for (pos in mItems.indices.reversed()) {
      val item = mItems[pos]
      // Keep going backwards until we find a time separator which has a start time before now
      if (item is TimeSeparatorItem && item.startTime < time) {
        return pos
      }
    }
    return 0
  }
  //endregion

  //region Sticky headers Override
  override fun isStickyHeader(position: Int): Boolean {
    return getItemViewType(position) == ITEM_TYPE_TIME_HEADER
  }

  override fun setupStickyHeaderView(stickyHeader: View?) {
    stickyHeader?.translationZ = stuckHeaderElevation
  }

  override fun teardownStickyHeaderView(stickyHeader: View?) {
    stickyHeader?.translationZ = 0f
  }
  //endregion

  private fun update() {
    mItems.clear()

    val filteredSessions = ArrayList<ScheduleSession>()

    if (sessionFilterList.isEmpty()) {
      filteredSessions.addAll(mSessions)
    } else {
      for (item in mSessions) {
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

    if (!mShowTimeSeparators) {
      mItems.addAll(filteredSessions)
    } else {
      var i = 0
      val size = filteredSessions.size
      while (i < size) {
        val prev = if (i > 0) filteredSessions[i - 1] else null
        val item = filteredSessions[i]

        if (prev == null || !ScheduleSessionHelper.sameStartTime(prev, item, true)) {
          mItems.add(TimeSeparatorItem(item))
        }
        mItems.add(item)
        i++
      }
    }

    // TODO use DiffUtil
    notifyDataSetChanged()
  }

  //region Time Separator
  private class TimeSeparatorViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mStartTime: TextView? = itemView as? TextView

    internal fun bind(item: TimeSeparatorItem) {
      mStartTime?.text = TimeUtils.formatShortTime(itemView.context, Date(item.startTime))
    }
  }

  private data class TimeSeparatorItem(val item: ScheduleSession) {
    val startTime: Long = item.startTimestamp
  }

  //endregion

  //region Session
  class SessionItemViewHolder internal constructor(itemView: View,
                                                   private val clickListener: (ScheduleSession) -> Unit,
                                                   private val context: Context)
    : RecyclerView.ViewHolder(itemView) {
    private var sessionLayout: ConstraintLayout = itemView.findViewById(R.id.sessionItemLayout)
    private var sessionTitle: TextView = itemView.findViewById(R.id.sessionTitleTextView)
    private var sessionDescription: TextView = itemView.findViewById(R.id.sessionDescriptionTextView)
    private var sessionBookmark: ImageButton = itemView.findViewById(R.id.bookmark)

    internal fun bind(scheduleSession: ScheduleSession, daySchedule: DaySchedule) {
      // Session title
      sessionTitle.text = scheduleSession.title

      val sessionDuration = TimeUtils.formatDuration(itemView.context,
          scheduleSession.startTimestamp, scheduleSession.endTimestamp)
      val roomTitle = getRoomTitle(scheduleSession, daySchedule)

      val descriptionBuilder = StringBuilder()
      val resources = itemView.resources

      if (EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED) {
        if (roomTitle.isEmpty()) {
          descriptionBuilder.append(resources.getString(R.string.session_description_placeholder,
              sessionDuration, EmojiCompat.get().process(scheduleSession.languageInEmoji)))
        } else {
          // We need to check the status of EmojiCompat if we want to avoid a crash at 1st launch
          descriptionBuilder.append(resources.getString(R.string.session_description_placeholder_with_language,
              sessionDuration, roomTitle, EmojiCompat.get().process(scheduleSession.languageInEmoji)))
        }
      } else {
        val languageStringRes = getLanguageFullName(scheduleSession.language)
        if (roomTitle.isEmpty()) {
          if (languageStringRes != 0) {
            descriptionBuilder.append(resources.getString(R.string.session_description_placeholder,
                sessionDuration, resources.getString(languageStringRes)))
          } else {
            descriptionBuilder.append(sessionDuration)
          }
        } else {
          if (languageStringRes != 0) {
            descriptionBuilder.append(resources.getString(R.string.session_description_placeholder_with_language,
                sessionDuration, roomTitle, resources.getString(languageStringRes)))
          } else {
            descriptionBuilder.append(resources.getString(R.string.session_description_placeholder,
                sessionDuration, roomTitle))
          }
        }
      }

      // Speakers
      if (scheduleSession.speakers.isNotEmpty()) {
        descriptionBuilder.append('\n')
        scheduleSession.speakers.forEachIndexed { index, speakerKt ->
          descriptionBuilder.append(speakerKt.name?.trim { it <= ' ' })
          if (index != scheduleSession.speakers.size - 1) descriptionBuilder.append(", ")
        }
      }

      sessionDescription.text = descriptionBuilder.toString()

      sessionLayout.setOnClickListener {
        clickListener.invoke(scheduleSession)
      }

      sessionBookmark.setOnClickListener {
        sessionBookmark.isActivated = !sessionBookmark.isActivated
        SessionSelector.setSessionSelected(scheduleSession.sessionId, sessionBookmark.isActivated)
        if (sessionBookmark.isActivated) {
          ScheduleSessionHelper.scheduleStarredSession(context,
              scheduleSession.startTimestamp,
              scheduleSession.endTimestamp,
              scheduleSession.sessionId)
        } else {
          ScheduleSessionHelper.unScheduleSession(context, scheduleSession.sessionId)
        }
      }
      sessionBookmark.isActivated = SessionSelector.isSelected(scheduleSession.sessionId)
    }
  }
  //endregion

  companion object {

    private val ID_ARRAY = LongArray(4)
    private const val ITEM_TYPE_SESSION = 0
    private const val ITEM_TYPE_BREAK = 1
    private const val ITEM_TYPE_TIME_HEADER = 2

    private fun getRoomTitle(scheduleSession: ScheduleSession, daySchedule: DaySchedule): String {
      var roomTitle = ""
      for (roomSchedule in daySchedule.roomSchedules) {
        if (roomSchedule.roomId == scheduleSession.roomId) {
          roomTitle = roomSchedule.title
        }
      }
      return roomTitle
    }

    private fun generateIdForScheduleItem(item: ScheduleSession): Long {
      val array = ID_ARRAY
      // This code may look complex but its pretty simple. We need to use stable ids so that
      // any user interaction animations are run correctly (such as ripples). This means that
      // we need to generate a stable id. Not all items have sessionIds so we generate one
      // using the sessionId, title, start time and end time.
      array[0] = item.sessionId.toLong()
      array[1] = (if (!TextUtils.isEmpty(item.title)) item.title.hashCode() else 0).toLong()
      array[2] = item.startTimestamp
      array[3] = item.endTimestamp
      return Arrays.hashCode(array).toLong()
    }

    @StringRes
    fun getLanguageFullName(abbreviatedVersion: String?): Int {
      if (!TextUtils.isEmpty(abbreviatedVersion)) {
        if ("en".equals(abbreviatedVersion, ignoreCase = true)) {
          return R.string.english
        } else if ("fr".equals(abbreviatedVersion, ignoreCase = true)) {
          return R.string.french
        }
      }
      return R.string.no_language
    }
  }

}
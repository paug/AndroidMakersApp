package fr.paug.androidmakers.ui.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.databinding.ActivityDetailBinding
import fr.paug.androidmakers.databinding.DetailViewSpeakerInfoElementBinding
import fr.paug.androidmakers.databinding.SmallSocialImageBinding
import fr.androidmakers.store.manager.FirebaseStore
import fr.androidmakers.store.model.*
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.ui.util.CheckableFloatingActionButton
import fr.paug.androidmakers.util.ScheduleSessionHelper
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.UIUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.util.*

class SessionDetailActivity : BaseActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private var sessionId: String = ""
    private var sessionStartDateInMillis: Long = 0
    private var sessionEndDateInMillis: Long = 0

    private var sessionDateAndRoom: String? = null
    private val speakersList = ArrayList<String>()
    private var sessionForShare: Session? = null

    val scope = object : CoroutineScope {
        override val coroutineContext = Dispatchers.Main + Job()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)
        sessionId = intent.getStringExtra(PARAM_SESSION_ID)!!
        val roomId = intent.getStringExtra(PARAM_SESSION_ROOM)

        scope.launch {
            combine(
                    FirebaseStore().getSession(sessionId),
                    FirebaseStore().getRoom(roomId!!)
            ) { session, room ->
                session to room
            }.collect {
                setupUI(
                        it.first,
                        it.second,
                        roomId)

            }
        }
    }

    fun setupUI(session: Session, room: Room, roomId: String) {
        // small hack for share
        // Ideally, we should only create the menu when we have the data
        sessionForShare = session
        sessionStartDateInMillis = intent.getLongExtra(PARAM_SESSION_START_DATE, -1)
        sessionEndDateInMillis = intent.getLongExtra(PARAM_SESSION_END_DATE, -1)

        setupFeedback(session, roomId, sessionStartDateInMillis)

        val sessionDate = DateUtils.formatDateRange(this,
                Formatter(resources.configuration.locale),
                sessionStartDateInMillis,
                sessionEndDateInMillis,
                DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR,
                null).toString()
        sessionDateAndRoom = if (!TextUtils.isEmpty(room.name))
            getString(R.string.sessionDateWithRoomPlaceholder, sessionDate, room.name)
        else
            sessionDate
        activityDetailBinding.sessionDateAndRoomTextView.text = sessionDateAndRoom

        activityDetailBinding.sessionTitleTextView.text = session.title
        activityDetailBinding.sessionDescriptionTextView.text = if (session.description != null)
            Html.fromHtml(session.description.trim { it <= ' ' })
        else
            ""

        val languageFullNameRes = session.language
        if (languageFullNameRes.isNotEmpty() == true) {
            activityDetailBinding.sessionLanguageChip.text = languageFullNameRes
            activityDetailBinding.sessionLanguageChip.visibility = View.VISIBLE
        }

        // Tags list
        if (session.tags.isNotEmpty() == true) {
            if (session.tags.first().isNotEmpty()) {
                activityDetailBinding.sessionTypeChip.text = session.tags.first()
                activityDetailBinding.sessionTypeChip.visibility = View.VISIBLE
            }
        }

        if (session.complexity.isNotEmpty() == true) {
            activityDetailBinding.sessionExperienceChip.text = session.complexity
            activityDetailBinding.sessionExperienceChip.visibility = View.VISIBLE
        }

        activityDetailBinding.scheduleFab.setOnClickListener { fab ->
            val isInSchedule = !(fab as CheckableFloatingActionButton).isChecked
            fab.isChecked = isInSchedule
            changeSessionSelection(isInSchedule)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                animateFab(fab, isInSchedule)
            }
        }

        val sessionSelected = SessionSelector.isSelected(sessionId)
        activityDetailBinding.scheduleFab.isChecked = sessionSelected

        setActionBar(session)
        setSpeakers(session)

        if (session.platformUrl != null) {
            activityDetailBinding.watchButton.visibility = View.VISIBLE
            activityDetailBinding.watchButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(session.platformUrl)))
            }
        }

        if (session.slido != null) {
            activityDetailBinding.slidoButton.visibility = View.VISIBLE
            activityDetailBinding.slidoButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(session.slido)))
            }
        }

        activityDetailBinding.separator2.visibility = View.VISIBLE
    }

    private fun setupFeedback(session: Session, roomId: String, sessionStartDateInMillis: Long) {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.share -> {
            sessionForShare?.let { shareSession(it) }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun setSpeakers(session: Session) {

        scope.launch(Dispatchers.Main) {
            val speakers = session.speakers.filter { !it.isNullOrBlank() }
                    .map {
                        async {
                            FirebaseStore().getSpeaker(it).first()
                        }
                    }
                    .awaitAll()
                    .filterNotNull()
            val sessionSpeakerLayout = findViewById<ViewGroup>(R.id.sessionSpeakerLayout)
            sessionSpeakerLayout.removeAllViews()

            speakers.forEach {speaker ->
                speakersList.add(speaker.getFullNameAndCompany())

                val speakerInfoElementBinding = DetailViewSpeakerInfoElementBinding.inflate(layoutInflater,
                        null,
                        false)
                speakerInfoElementBinding.speakerBio.movementMethod = LinkMovementMethod.getInstance()

                setSpeakerSocialNetworkHandle(speaker, speakerInfoElementBinding)
                sessionSpeakerLayout.addView(speakerInfoElementBinding.root)
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    internal fun animateFab(fab: CheckableFloatingActionButton, isInSchedule: Boolean) {
        (ContextCompat.getDrawable(
                this, if (isInSchedule) R.drawable.avd_bookmark else R.drawable.avd_unbookmark) as? AnimatedVectorDrawable)?.also { avd ->
            fab.setImageDrawable(avd)
            val backgroundColor = ObjectAnimator.ofArgb(
                    fab,
                    UIUtils.BACKGROUND_TINT,
                    if (isInSchedule)
                        Color.WHITE
                    else
                        ContextCompat.getColor(this, R.color.colorAccent))
            backgroundColor.duration = 400L
            backgroundColor.interpolator = AnimationUtils.loadInterpolator(this,
                    android.R.interpolator.fast_out_slow_in)
            backgroundColor.start()
            avd.start()
        }
    }

    private fun setSpeakerSocialNetworkHandle(speaker: Speaker, speakerInfoElementBinding: DetailViewSpeakerInfoElementBinding) {
        if (speaker.socials != null && speaker.socials!!.isNotEmpty()) {
            for (social in speaker.socials!!) {
                val socialNetworkHandle = SocialNetworkHandle(social?.icon, social?.link)
                if (socialNetworkHandle.networkType != SocialNetworkHandle.SocialNetworkType.Unknown) {
                    val smallSocialImageBinding = SmallSocialImageBinding.inflate(layoutInflater, null, false)
                    smallSocialImageBinding.image.setOnClickListener {
                        if (BuildConfig.DEBUG) {
                            Log.d(SessionDetailActivity::class.java.name, "User clicked on social handle with name=" + socialNetworkHandle.networkType)
                        }
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(social?.link)))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    speakerInfoElementBinding.speakerSocialNetworkHandleLayout.addView(smallSocialImageBinding.root)
                }
            }
        } else {
            speakerInfoElementBinding.speakerSocialNetworkHandleLayout.visibility = View.GONE
        }
    }

    private fun setActionBar(session: Session?) {
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = session?.title
        }
    }

    private fun changeSessionSelection(select: Boolean) {
        SessionSelector.setSessionSelected(sessionId, select)
        toggleScheduleSessionNotification(select)
    }

    private fun toggleScheduleSessionNotification(select: Boolean) {
        if (select) {
            ScheduleSessionHelper.scheduleStarredSession(this, sessionStartDateInMillis, sessionEndDateInMillis, sessionId)
        } else {
            ScheduleSessionHelper.unScheduleSession(this, sessionId)
        }
    }

    private fun shareSession(session: Session) {
        val speakers = TextUtils.join(", ", speakersList)

        val shareSessionIntent = Intent(Intent.ACTION_SEND)
        shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session.title)
        if (speakersList.isEmpty()) {
            shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format("%s: %s (%s)", getString(R.string.app_name), session.title, sessionDateAndRoom))
        } else {
            shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format("%s: %s (%s, %s, %s)", getString(R.string.app_name), session.title, speakers, sessionDateAndRoom, session.language))
        }
        shareSessionIntent.type = "text/plain"
        startActivity(shareSessionIntent)
    }

    companion object {
        private const val PARAM_SESSION_ID = "param_session_id"
        private const val PARAM_SESSION_START_DATE = "param_session_start_date"
        private const val PARAM_SESSION_END_DATE = "param_session_end_date"
        private const val PARAM_SESSION_ROOM = "param_session_room"

        fun startActivity(context: Context, scheduleSession: ScheduleSession) {
            val intent = Intent(context, SessionDetailActivity::class.java)
            intent.putExtra(PARAM_SESSION_ID, scheduleSession.sessionId)
            intent.putExtra(PARAM_SESSION_START_DATE, scheduleSession.startTimestamp)
            intent.putExtra(PARAM_SESSION_END_DATE, scheduleSession.endTimestamp)
            intent.putExtra(PARAM_SESSION_ROOM, scheduleSession.roomId)
            context.startActivity(intent)
        }

        fun startActivity(context: Context, sessionId: String, sessionStartDateInMillis: Long,
                          sessionEndDateInMillis: Long, roomId: String?) {
            val intent = Intent(context, SessionDetailActivity::class.java)
            intent.putExtra(PARAM_SESSION_ID, sessionId)
            intent.putExtra(PARAM_SESSION_START_DATE, sessionStartDateInMillis)
            intent.putExtra(PARAM_SESSION_END_DATE, sessionEndDateInMillis)
            intent.putExtra(PARAM_SESSION_ROOM, roomId)
            context.startActivity(intent)
        }
    }

}
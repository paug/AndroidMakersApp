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
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.databinding.ActivityDetailBinding
import fr.paug.androidmakers.databinding.DetailViewSpeakerInfoElementBinding
import fr.paug.androidmakers.databinding.SmallRibbonImageBinding
import fr.paug.androidmakers.databinding.SmallSocialImageBinding
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.model.*
import fr.paug.androidmakers.ui.adapter.ScheduleSessionKt
import fr.paug.androidmakers.ui.util.CheckableFloatingActionButton
import fr.paug.androidmakers.util.ScheduleSessionHelper
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.UIUtils
import fr.paug.androidmakers.util.YoutubeUtil
import java.util.*

/**
 * Details of a session
 *
 * Nice improvements to have : session rate/feedback
 */
class SessionDetailActivity : BaseActivity(), YouTubeThumbnailView.OnInitializedListener {

    private var sessionId: String = ""
    private var sessionStartDateInMillis: Long = 0
    private var sessionEndDateInMillis: Long = 0

    private var session: SessionKt? = null
    private var sessionDateAndRoom: String? = null
    private val speakersList = ArrayList<String>()
    private var videoID: String? = null
    private var playButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
        sessionId = intent.getStringExtra(PARAM_SESSION_ID)
        //session = AgendaRepository.instance.getSession(sessionId)

        AndroidMakersStore().getSession(sessionId) {
            session = it
            setUpSession(activityDetailBinding)
        }

    }

    private fun setUpSession(activityDetailBinding: ActivityDetailBinding) {
        sessionStartDateInMillis = intent.getLongExtra(PARAM_SESSION_START_DATE, -1)
        sessionEndDateInMillis = intent.getLongExtra(PARAM_SESSION_END_DATE, -1)

//        val sessionRoom = AgendaRepository.instance.getRoom(intent.getIntExtra(PARAM_SESSION_ROOM, -1))
        val sessionRoom = Room("") //TODO

        if (session == null) {
            // We have a problem !
            activityDetailBinding.sessionInformationsScrollView.visibility = View.GONE
            activityDetailBinding.errorMessageTextView.visibility = View.VISIBLE
            return
        }

        val sessionDate = DateUtils.formatDateRange(this,
                Formatter(resources.configuration.locale),
                sessionStartDateInMillis,
                sessionEndDateInMillis,
                DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR, null).toString()
        sessionDateAndRoom = if (sessionRoom != null && !TextUtils.isEmpty(sessionRoom.name))
            getString(R.string.sessionDateWithRoomPlaceholder, sessionDate, sessionRoom.name)
        else
            sessionDate

        activityDetailBinding.sessionTitleTextView.text = session!!.title
        activityDetailBinding.sessionDateAndRoomTextView.text = sessionDateAndRoom
        activityDetailBinding.sessionDescriptionTextView.movementMethod = LinkMovementMethod.getInstance()
        activityDetailBinding.sessionDescriptionTextView.text = if (session!!.description != null)
            Html.fromHtml(session!!.description!!.trim { it <= ' ' })
        else
            ""

        val languageFullNameRes = session?.language
        if (languageFullNameRes?.isNotEmpty() == true) {
            activityDetailBinding.sessionLanguageChip.text = languageFullNameRes
        } else {
            activityDetailBinding.sessionLanguageChip.visibility = View.GONE
        }

        // Tags list
        activityDetailBinding.sessionTypeChip.visibility = View.GONE
        activityDetailBinding.sessionSubTypeChip.visibility = View.GONE
        if (session?.tags?.isNotEmpty() == true) {
            if (session?.tags?.first().isNullOrEmpty().not()) {
                activityDetailBinding.sessionTypeChip.text = session?.tags?.first()
                activityDetailBinding.sessionTypeChip.visibility = View.VISIBLE
            }
        }

        if (session?.complexity != null) {
            activityDetailBinding.sessionExperienceChip.text = session?.complexity
        } else {
            activityDetailBinding.sessionExperienceChip.visibility = View.GONE
        }

        //TODO get speakers
//        val sessionSpeakerLayout = findViewById<ViewGroup>(R.id.sessionSpeakerLayout)
//        if (session!!.speakers != null && session!!.speakers.size > 0) {
//            for (speakerID in session!!.speakers) {
//                val speaker = AgendaRepository.instance.getSpeaker(speakerID)
//                if (speaker != null) {
//                    speakersList.add(speaker.fullNameAndCompany)
//
//                    val speakerInfoElementBinding = DataBindingUtil.inflate<DetailViewSpeakerInfoElementBinding>(layoutInflater,
//                            R.layout.detail_view_speaker_info_element, null,
//                            false)
//                    speakerInfoElementBinding.speakerBio.movementMethod = LinkMovementMethod.getInstance()
//                    speakerInfoElementBinding.speaker = speaker
//
//                    setSpeakerSocialNetworkHandle(speaker, speakerInfoElementBinding)
//                    setSpeakerRibbons(speaker, speakerInfoElementBinding)
//
//                    sessionSpeakerLayout.addView(speakerInfoElementBinding.root)
//                }
//            }
//        }

        activityDetailBinding.scheduleFab.setOnClickListener { fab ->
            val isInSchedule = !(fab as CheckableFloatingActionButton).isChecked
            fab.isChecked = isInSchedule
            changeSessionSelection(isInSchedule)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                animateFab(fab, isInSchedule)
            }
        }

        val sessionSelected = SessionSelector.getInstance().isSelected(sessionId)
        activityDetailBinding.scheduleFab.isChecked = sessionSelected

        setActionBar(session)

        setVideoThumbnail(activityDetailBinding)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    internal fun animateFab(fab: CheckableFloatingActionButton, isInSchedule: Boolean?) {
        val avd = ContextCompat.getDrawable(
                this, if (isInSchedule!!) R.drawable.avd_bookmark else R.drawable.avd_unbookmark) as AnimatedVectorDrawable?
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
        avd!!.start()
    }

    private fun setSpeakerSocialNetworkHandle(speaker: Speaker, speakerInfoElementBinding: DetailViewSpeakerInfoElementBinding) {
        if (speaker.socialNetworkHandles != null && speaker.socialNetworkHandles.size > 0) {
            for (socialNetworkHandle in speaker.socialNetworkHandles) {
                if (socialNetworkHandle.networkType != SocialNetworkHandle.SocialNetworkType.Unknown) {
                    val smallSocialImageBinding = DataBindingUtil.inflate<SmallSocialImageBinding>(layoutInflater, R.layout.small_social_image, null, false)
                    smallSocialImageBinding.socialHandle = socialNetworkHandle
                    smallSocialImageBinding.image.setOnClickListener {
                        if (BuildConfig.DEBUG) {
                            Log.d(SessionDetailActivity::class.java.name, "User clicked on social handle with name=" + socialNetworkHandle.networkType.name)
                        }
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(socialNetworkHandle.link)))
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

    private fun setSpeakerRibbons(speaker: Speaker, speakerInfoElementBinding: DetailViewSpeakerInfoElementBinding) {
        if (speaker.ribbonList != null && speaker.ribbonList.size > 0) {
            for (ribbon in speaker.ribbonList) {
                if (ribbon.ribbonType != Ribbon.RibbonType.NONE) {
                    val smallRibbonImageBinding = DataBindingUtil.inflate<SmallRibbonImageBinding>(layoutInflater, R.layout.small_ribbon_image, null, false)
                    smallRibbonImageBinding.ribbon = ribbon
                    smallRibbonImageBinding.image.setOnClickListener {
                        if (BuildConfig.DEBUG) {
                            Log.d(SessionDetailActivity::class.java.name, "User clicked on ribbon with name=" + ribbon.ribbonType.name)
                        }
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ribbon.link)))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    speakerInfoElementBinding.speakerRibbonLayout.addView(smallRibbonImageBinding.root)
                } else {
                    speakerInfoElementBinding.speakerRibbonLayout.visibility = View.GONE
                }
            }
        } else {
            speakerInfoElementBinding.speakerRibbonLayout.visibility = View.GONE
        }
    }

    private fun setActionBar(session: SessionKt?) {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = session!!.title
        }
    }

    private fun changeSessionSelection(select: Boolean) {
        SessionSelector.getInstance().setSessionSelected(sessionId, select)
        toggleScheduleSessionNotification(select)
    }

    private fun toggleScheduleSessionNotification(select: Boolean) {
        if (select) {
            ScheduleSessionHelper.scheduleStarredSession(this, sessionStartDateInMillis, sessionEndDateInMillis, sessionId)
        } else {
            ScheduleSessionHelper.unScheduleSession(this, sessionId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.share -> {
                shareSession()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun shareSession() {
        val speakers = TextUtils.join(", ", speakersList)

        val shareSessionIntent = Intent(Intent.ACTION_SEND)
        shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session!!.title)
        if (speakersList.isEmpty()) {
            shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format("%s: %s (%s)", getString(R.string.app_name), session!!.title, sessionDateAndRoom))
        } else {
            shareSessionIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format("%s: %s (%s, %s, %s)", getString(R.string.app_name), session!!.title, speakers, sessionDateAndRoom, session!!.language))
        }
        shareSessionIntent.type = "text/plain"
        startActivity(shareSessionIntent)
    }

    // region Video management
    private fun setVideoThumbnail(activityDetailBinding: ActivityDetailBinding) {
        if (TextUtils.isEmpty(session!!.videoURL))
            return

        videoID = YoutubeUtil.getVideoID(session!!.videoURL)
        if (!TextUtils.isEmpty(videoID)) {
            playButton = activityDetailBinding.playButton
            activityDetailBinding.videoThumbnail.initialize(BuildConfig.YOUTUBE_API_KEY, this)
            activityDetailBinding.videoThumbnail.setOnClickListener {
                val videoUri = YoutubeUtil.getVideoUri(session!!.videoURL!!)
                if (videoUri != null) {
                    startActivity(Intent(Intent.ACTION_VIEW, videoUri))
                } else {
                    Toast.makeText(this@SessionDetailActivity, R.string.unable_to_launch_video_intent, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView, youTubeThumbnailLoader: YouTubeThumbnailLoader) {
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
            override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                if (BuildConfig.DEBUG) {
                    Log.d(SessionDetailActivity::class.java.name, "onThumbnailLoaded")
                }
                youTubeThumbnailLoader.release()
            }

            override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {
                if (BuildConfig.DEBUG) {
                    Log.d(SessionDetailActivity::class.java.name, "onThumbnailError")
                }
                youTubeThumbnailLoader.release()
            }
        })
        // TODO : placeholder ?
        youTubeThumbnailLoader.setVideo(videoID)
        youTubeThumbnailView.visibility = View.VISIBLE
        playButton!!.visibility = View.VISIBLE
    }

    override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView, youTubeInitializationResult: YouTubeInitializationResult) {
        youTubeThumbnailView.visibility = View.GONE
        playButton!!.visibility = View.GONE
    }

    companion object {

        private val PARAM_SESSION_ID = "param_session_id"
        private val PARAM_SESSION_START_DATE = "param_session_start_date"
        private val PARAM_SESSION_END_DATE = "param_session_end_date"
        private val PARAM_SESSION_ROOM = "param_session_room"

        fun startActivity(context: Context, scheduleSession: ScheduleSessionKt) {
            val intent = Intent(context, SessionDetailActivity::class.java)
            intent.putExtra(PARAM_SESSION_ID, scheduleSession.sessionId)
            intent.putExtra(PARAM_SESSION_START_DATE, scheduleSession.startTimestamp)
            intent.putExtra(PARAM_SESSION_END_DATE, scheduleSession.endTimestamp)
            intent.putExtra(PARAM_SESSION_ROOM, scheduleSession.roomId)
            context.startActivity(intent)
        }

        fun startActivity(context: Context, sessionId: Int, sessionStartDateInMillis: Long,
                          sessionEndDateInMillis: Long, roomId: Int) {
            val intent = Intent(context, SessionDetailActivity::class.java)
            intent.putExtra(PARAM_SESSION_ID, sessionId)
            intent.putExtra(PARAM_SESSION_START_DATE, sessionStartDateInMillis)
            intent.putExtra(PARAM_SESSION_END_DATE, sessionEndDateInMillis)
            intent.putExtra(PARAM_SESSION_ROOM, roomId)
            context.startActivity(intent)
        }
    }

    // endregion Video management

}

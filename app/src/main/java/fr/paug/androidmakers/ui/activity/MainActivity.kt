package fr.paug.androidmakers.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.paug.androidmakers.R
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.ui.fragment.AboutFragment
import fr.paug.androidmakers.ui.fragment.AgendaFragment
import fr.paug.androidmakers.ui.fragment.VenuePagerFragment
import fr.paug.androidmakers.util.TimeUtils
import java.text.SimpleDateFormat

class MainActivity : BaseActivity() {

    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    private var navigation: BottomNavigationView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val tag = when (item.itemId) {
            R.id.navigation_agenda -> TAG_FRAGMENT_AGENDA
            R.id.navigation_venue -> TAG_FRAGMENT_VENUE
            R.id.navigation_about -> TAG_FRAGMENT_ABOUT
            else -> return@OnNavigationItemSelectedListener false
        }

        fragment = fragmentManager?.findFragmentByTag(tag)

        if (fragment == null)
            fragment = newFragmentByTag(tag)

        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment!!, tag)
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager

        if (savedInstanceState == null) {
            addAgenda()
        }

        navigation = findViewById(R.id.navigation)
        navigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        handleURLLink(intent)
    }

    private fun addAgenda() {
        fragment = AgendaFragment()
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.fragment_container, fragment!!, TAG_FRAGMENT_AGENDA).commit()
    }

    private fun newFragmentByTag(tag: String): Fragment = when (tag) {
        TAG_FRAGMENT_VENUE -> VenuePagerFragment()
        TAG_FRAGMENT_ABOUT -> AboutFragment()
        TAG_FRAGMENT_AGENDA -> AgendaFragment()
        else -> AgendaFragment()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleURLLink(intent)
    }

    private fun handleURLLink(intent: Intent) {
        // Do not handle the Deep link if user is coming from recent tasks
        if (intent.flags != Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) {
            val data = intent.data
            if (data != null && data.host!!.equals("androidmakers.fr", ignoreCase = true)) {
                val pathSegments = data.pathSegments
                if (pathSegments != null && pathSegments.size > 0) {
                    val path = pathSegments[0]
                    if (path.equals("schedule", ignoreCase = true)) {
                        handleScheduleLink(data)
                    } else if (path.equals("logistics", ignoreCase = true)) {
                        handleLogisticsLink()
                    }
                }
            }
        }
    }

    private fun handleLogisticsLink() {
        if (navigation != null) {
            navigation!!.selectedItemId = R.id.navigation_venue
        }
    }

    private fun handleScheduleLink(data: Uri) {
        if (navigation != null) {
            navigation!!.selectedItemId = R.id.navigation_agenda
        }

        val sessionId = data.getQueryParameter("sessionId")

        if (sessionId != null) {
            AndroidMakersStore().getSlots { slots ->
                for (scheduleSlot in slots) {
                    if (scheduleSlot.sessionId == sessionId) {
                        val format = SimpleDateFormat(TimeUtils.dateFormat)
                        val startTimestamp = format.parse(scheduleSlot.startDate).time
                        val endTimestamp = format.parse(scheduleSlot.endDate).time
                        SessionDetailActivity.startActivity(this, sessionId, startTimestamp, endTimestamp, scheduleSlot.roomId)
                        break
                    }
                }

            }
        }
    }

    companion object {
        private val TAG_FRAGMENT_AGENDA = "TAG_FRAGMENT_AGENDA"
        private val TAG_FRAGMENT_VENUE = "TAG_FRAGMENT_VENUE"
        private val TAG_FRAGMENT_ABOUT = "TAG_FRAGMENT_ABOUT"
    }

}
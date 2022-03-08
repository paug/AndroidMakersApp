package fr.paug.androidmakers.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

import fr.paug.androidmakers.R
import fr.paug.androidmakers.service.SessionAlarmService
import fr.paug.androidmakers.ui.adapter.ScheduleSession

object ScheduleSessionHelper {

    private val ALLOWED_OVERLAP = (5 * 60 * 1000).toLong() // 5 minutes

    private var toast: Toast? = null

    fun sameStartTime(block1: ScheduleSession, block2: ScheduleSession, useOverlap: Boolean): Boolean {
        return Math.abs(block1.startTimestamp - block2.startTimestamp) <= if (useOverlap) ALLOWED_OVERLAP else 0
    }

    //region Scheduling session
    fun scheduleStarredSession(context: Context, sessionStartDateInMillis: Long, sessionEndDateInMillis: Long, sessionId: String) {
        Log.d("Schedule session", "Scheduling notification for session $sessionId")

        if (toast != null) toast?.cancel()
        toast = Toast.makeText(context, R.string.session_selected, Toast.LENGTH_SHORT)
        toast?.show()

        val scheduleIntent = Intent(SessionAlarmService.ACTION_SCHEDULE_STARRED_BLOCK)
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_START, sessionStartDateInMillis)
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_END, sessionEndDateInMillis)
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId)
        SessionAlarmService.enqueueWork(context, scheduleIntent)
    }

    fun unScheduleSession(context: Context, sessionId: String) {
        Log.d("Schedule session", "Unscheduling notification for session $sessionId")

        if (toast != null) toast?.cancel()
        toast = Toast.makeText(context, R.string.session_deselected, Toast.LENGTH_SHORT)
        toast?.show()

        val scheduleIntent = Intent(SessionAlarmService.ACTION_UNSCHEDULE_UNSTARRED_BLOCK)
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId)
        SessionAlarmService.enqueueWork(context, scheduleIntent)
    }

    fun cancelToast() {
        toast?.cancel()
        toast = null
    }
    //endregion

}
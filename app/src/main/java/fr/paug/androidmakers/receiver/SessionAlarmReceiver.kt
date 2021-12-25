package fr.paug.androidmakers.receiver

import fr.paug.androidmakers.service.SessionAlarmService.Companion.ACTION_NOTIFY_SESSION
import fr.paug.androidmakers.service.SessionAlarmService.Companion.enqueueWork
import fr.paug.androidmakers.service.SessionAlarmService.Companion.ACTION_SCHEDULE_ALL_STARRED_BLOCKS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import fr.paug.androidmakers.service.SessionAlarmService

// On boot, reschedule all favorites sessions
class SessionAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (ACTION_NOTIFY_SESSION == action) {
            val scheduleIntent = Intent(ACTION_NOTIFY_SESSION)
                .setData(intent.data)
                .putExtras(intent)
            enqueueWork(context, scheduleIntent)
        } else if (Intent.ACTION_BOOT_COMPLETED == action) {
            val scheduleIntent = Intent(ACTION_SCHEDULE_ALL_STARRED_BLOCKS)
            enqueueWork(context, scheduleIntent)
        }
    }
}
package com.androidmakers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionAlarmReceiver: BroadcastReceiver(), KoinComponent {
    private val notificationUtils: NotificationUtils by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("PLOP", "Receive intent")
    }
}

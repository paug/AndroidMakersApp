package com.androidmakers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import fr.androidmakers.domain.repo.SessionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionAlarmReceiver: BroadcastReceiver(), KoinComponent {
    private val notificationUtils: NotificationUtils by inject()
    private val sessionsRepository: SessionsRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {

        val sessionId = intent?.getStringExtra(SESSION_ID) ?: ""
        CoroutineScope(Dispatchers.Default).launch {
            val session = sessionsRepository.getSession(sessionId).first().getOrNull()
            session?.let {
                MainScope().launch {
                    notificationUtils.triggerNotification(it)
                }
            }
        }
    }

    companion object {
        const val SESSION_ID = "sessionId"
    }
}

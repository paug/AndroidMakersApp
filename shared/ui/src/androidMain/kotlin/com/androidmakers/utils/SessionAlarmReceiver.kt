package com.androidmakers.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.androidmakers.ui.agenda.toUISession
import fr.androidmakers.domain.repo.RoomsRepository
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
    private val roomsRepository: RoomsRepository by inject()

    override fun onReceive(context: Context?, intent: Intent?) {

        // TODO Schedule the whole agenda when device boot

        val sessionId = intent?.getStringExtra(SESSION_ID) ?: ""
        CoroutineScope(Dispatchers.Default).launch {
            val session = sessionsRepository.getSession(sessionId).first().getOrNull()
            val rooms = roomsRepository.getRooms().first().getOrNull() ?: emptyList()
            session?.let {
                MainScope().launch {
                    // TODO Put this in a shared usecase
                    val uiSession = it.toUISession(
                        rooms = rooms.associateBy { it.id },
                        isFavorite = false,
                        speakers = emptyMap()
                    )
                    notificationUtils.triggerNotification(uiSession)
                }
            }
        }
    }

    companion object {
        const val SESSION_ID = "sessionId"
    }
}

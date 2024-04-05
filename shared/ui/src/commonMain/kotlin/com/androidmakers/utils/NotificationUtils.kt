package com.androidmakers.utils

import com.androidmakers.ui.model.UISession
import kotlin.time.Duration.Companion.minutes

expect class NotificationUtils {

    fun initNotifications()

    fun triggerNotification(session: UISession)

    fun scheduleNotification(session: UISession)

    fun cancelNotification(session: UISession)
}

val NOTIFICATION_REMINDER_DELAY = 15.minutes

enum class NotificationChannels(val id: String) {
    TALKS("123"),
    GENERAL("666")
}

internal fun UISession.notificationId() = "session_$id".hashCode()

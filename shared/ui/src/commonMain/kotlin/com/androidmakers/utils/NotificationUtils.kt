package com.androidmakers.utils

import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.utils.formatShortTime
import kotlin.time.Duration.Companion.minutes

expect class NotificationUtils {

    fun initNotifications()

    fun triggerNotification(session: Session)

    fun scheduleNotification(session: UISession)

    fun cancelNotification(session: UISession)
}

val NOTIFICATION_REMINDER_DELAY = 15.minutes
fun UISession.scheduledNotificationDate() = startDate - NOTIFICATION_REMINDER_DELAY

enum class NotificationChannels(val id: String) {
    TALKS("123"),
    GENERAL("666")
}

// TODO merge this
internal fun Session.notificationId() = "session_$id".hashCode()
internal fun UISession.notificationId() = "session_$id".hashCode()

internal fun UISession.notificationDisplayText() = "${startDate.formatShortTime()}, $room"

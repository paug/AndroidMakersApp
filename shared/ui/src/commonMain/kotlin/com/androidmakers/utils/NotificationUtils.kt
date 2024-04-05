package com.androidmakers.utils

import com.androidmakers.ui.model.UISession

expect class NotificationUtils {
    fun initNotifications()

    fun triggerNotification(session: UISession)
}

enum class NotificationChannels(val id: String) {
    TALKS("123"),
    GENERAL("666")
}

internal fun UISession.notificationId() = "session_$id".hashCode()

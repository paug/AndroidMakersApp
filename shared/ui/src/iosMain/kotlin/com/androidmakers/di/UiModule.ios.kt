package com.androidmakers.di

import com.androidmakers.utils.NotificationUtils
import org.koin.dsl.module

actual val uiModule = module {
    single { NotificationUtils() }
}

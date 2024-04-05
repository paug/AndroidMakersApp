package com.androidmakers.di

import com.androidmakers.utils.NotificationUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val uiModule = module {
    single { NotificationUtils(androidContext()) }
}

package fr.paug.androidmakers.wear.di

import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val messagingModule = module {
  factory<MessageClient> { Wearable.getMessageClient(androidContext()) }
}

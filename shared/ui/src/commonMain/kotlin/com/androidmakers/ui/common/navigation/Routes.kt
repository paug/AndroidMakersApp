package com.androidmakers.ui.common.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

// Top-level tab routes (bottom nav)
@Serializable data object FeedKey : NavKey
@Serializable data object AgendaKey : NavKey
@Serializable data object SpeakersKey : NavKey
@Serializable data object SponsorsKey : NavKey
@Serializable data object InfoKey : NavKey

// Legacy keys kept for serialization backward compat
@Serializable data object VenueKey : NavKey
@Serializable data object AboutKey : NavKey

// Detail routes (full-screen, no bottom nav)
@Serializable data class SessionDetailKey(val sessionId: String) : NavKey
@Serializable data class SpeakerDetailKey(val speakerId: String) : NavKey

val navKeySavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(FeedKey::class, FeedKey.serializer())
            subclass(AgendaKey::class, AgendaKey.serializer())
            subclass(SpeakersKey::class, SpeakersKey.serializer())
            subclass(SponsorsKey::class, SponsorsKey.serializer())
            subclass(InfoKey::class, InfoKey.serializer())
            subclass(VenueKey::class, VenueKey.serializer())
            subclass(AboutKey::class, AboutKey.serializer())
            subclass(SessionDetailKey::class, SessionDetailKey.serializer())
            subclass(SpeakerDetailKey::class, SpeakerDetailKey.serializer())
        }
    }
}

fun NavKey.isTabKey(): Boolean =
    this is FeedKey || this is AgendaKey ||
        this is SpeakersKey || this is SponsorsKey || this is InfoKey

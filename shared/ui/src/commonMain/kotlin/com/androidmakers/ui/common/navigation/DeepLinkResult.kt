package com.androidmakers.ui.common.navigation

import androidx.navigation3.runtime.NavKey

data class DeepLinkResult(val tabKey: NavKey, val detailKey: NavKey)

/**
 * Parse a deep link URI into a [DeepLinkResult].
 *
 * Supported patterns:
 * - `https://androidmakers.fr/session/{sessionId}` -> Agenda tab + SessionDetail
 * - `https://androidmakers.fr/speaker/{speakerId}` -> Speakers tab + SpeakerDetail
 */
fun parseDeepLink(uri: String): DeepLinkResult? {
    val sessionRegex = Regex("https?://androidmakers\\.fr/session/([^/]+)")
    val speakerRegex = Regex("https?://androidmakers\\.fr/speaker/([^/]+)")

    sessionRegex.matchEntire(uri)?.let { match ->
        val sessionId = match.groupValues[1]
        return DeepLinkResult(AgendaKey, SessionDetailKey(sessionId))
    }

    speakerRegex.matchEntire(uri)?.let { match ->
        val speakerId = match.groupValues[1]
        return DeepLinkResult(SpeakersKey, SpeakerDetailKey(speakerId))
    }

    return null
}

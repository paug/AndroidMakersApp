package fr.androidmakers.domain.model

import okio.Buffer
import okio.ByteString.Companion.decodeBase64

data class User(
    val id: String,
    val photoUrl: String?,
    val idToken: String?
)
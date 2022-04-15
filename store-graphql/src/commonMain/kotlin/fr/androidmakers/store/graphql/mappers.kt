package fr.androidmakers.store.graphql

import fr.androidmakers.store.graphql.fragment.RoomDetails
import fr.androidmakers.store.graphql.fragment.SessionDetails
import fr.androidmakers.store.graphql.fragment.SpeakerDetails
import fr.androidmakers.store.model.*

fun SpeakerDetails.toSpeaker(): Speaker {
    return Speaker(
        id = id,
        name = name,
        badges = emptyList(),
        bio = bio,
        company = company,
        companyLogo = companyLogo,
        country = country,
        featured = featured,
        order = order?.toInt(),
        photoUrl = photoUrl,
        socials = socials.map {
            it.toSocialsItem()
        }
    )
}

fun SpeakerDetails.Social.toSocialsItem(): SocialsItem {
    return SocialsItem(
            name = name,
            icon = icon,
            link = link,
    )
}

fun RoomDetails.toRoom(): Room {
    return Room(
            id = id,
            name = name,
    )
}

fun SessionDetails.toSession(): Session {
    return Session(
            id = id,
            description = description,
            title = title,
            complexity = complexity ?: "",
            language = language ?: "",
            platformUrl = "",
            slido = "",
            speakers = speakers.map { it.id },
            tags = tags,
            videoURL = ""
    )
}

fun SessionDetails.toSlot(): ScheduleSlot {
    return ScheduleSlot(
            sessionId = id,
            roomId = room.id,
            startDate = startDate,
            endDate = endDate
    )
}

fun GetVenueQuery.Venue.toVenue(): Venue {
    return Venue(
        name = name,
        address = address ?: "",
        coordinates = coordinates ?:"",
        descriptionFr = descriptionFr ,
        description = description ,
        imageUrl = imageUrl
    )
}

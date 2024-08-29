package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubInstanceConfigurationEntity(
    val urls: Urls? = null,
    val vapid: Vapid? = null,
    val accounts: Accounts,
    val statuses: Statuses,
    @SerialName("media_attachments") val mediaAttachments: MediaAttachments,
    val polls: Polls,
    val translation: Translation? = null,
) {

    @Serializable
    data class Urls(val streaming: String)

    @Serializable
    data class Vapid(@SerialName("public_key") val publicKey: String)

    @Serializable
    data class Accounts(
        @SerialName("max_featured_tags") val maxFeaturedTags: Int,
        @SerialName("max_pinned_statuses") val maxPinnedStatuses: Int? = null,
    )

    @Serializable
    data class Statuses(
        @SerialName("max_characters") val maxCharacters: Int,
        @SerialName("max_media_attachments") val maxMediaAttachments: Int,
        @SerialName("characters_reserved_per_url") val charactersReservedPerUrl: Int,
    )

    @Serializable
    data class MediaAttachments(
        @SerialName("supported_mime_types") val supportedMimeTypes: List<String>,
        @SerialName("image_size_limit") val imageSizeLimit: Int,
        @SerialName("image_matrix_limit") val imageMatrixLimit: Int,
        @SerialName("video_size_limit") val videoSizeLimit: Int,
        @SerialName("video_frame_rate_limit") val videoFrameRateLimit: Int,
        @SerialName("video_matrix_limit") val videoMatrixLimit: Int,
    )

    @Serializable
    data class Polls(
        @SerialName("max_options") val maxOptions: Int,
        @SerialName("max_characters_per_option") val maxCharactersPerOption: Int,
        @SerialName("min_expiration") val minExpiration: Int,
        @SerialName("max_expiration") val maxExpiration: Int,
    )

    @Serializable
    data class Translation(
        val enabled: Boolean,
    )
}

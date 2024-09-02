package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubInstanceConfigurationEntity(
    val urls: Urls? = null,
    val vapid: Vapid? = null,
    val accounts: Accounts? = null,
    val statuses: Statuses? = null,
    @SerialName("media_attachments") val mediaAttachments: MediaAttachments? = null,
    val polls: Polls? = null,
    val translation: Translation? = null,
) {

    @Serializable
    data class Urls(val streaming: String = "")

    @Serializable
    data class Vapid(@SerialName("public_key") val publicKey: String = "")

    @Serializable
    data class Accounts(
        @SerialName("max_featured_tags") val maxFeaturedTags: Int = 0,
        @SerialName("max_pinned_statuses") val maxPinnedStatuses: Int? = null,
    )

    @Serializable
    data class Statuses(
        @SerialName("max_characters") val maxCharacters: Int = 0,
        @SerialName("max_media_attachments") val maxMediaAttachments: Int = 0,
        @SerialName("characters_reserved_per_url") val charactersReservedPerUrl: Int = 0,
    )

    @Serializable
    data class MediaAttachments(
        @SerialName("supported_mime_types") val supportedMimeTypes: List<String> = emptyList(),
        @SerialName("image_size_limit") val imageSizeLimit: Int = 0,
        @SerialName("image_matrix_limit") val imageMatrixLimit: Int = 0,
        @SerialName("video_size_limit") val videoSizeLimit: Int = 0,
        @SerialName("video_frame_rate_limit") val videoFrameRateLimit: Int = 0,
        @SerialName("video_matrix_limit") val videoMatrixLimit: Int = 0,
    )

    @Serializable
    data class Polls(
        @SerialName("max_options") val maxOptions: Int = 0,
        @SerialName("max_characters_per_option") val maxCharactersPerOption: Int = 0,
        @SerialName("min_expiration") val minExpiration: Int = 0,
        @SerialName("max_expiration") val maxExpiration: Int = 0,
    )

    @Serializable
    data class Translation(
        val enabled: Boolean = false,
    )
}

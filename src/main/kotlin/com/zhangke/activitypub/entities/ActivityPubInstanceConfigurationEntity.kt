package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubInstanceConfigurationEntity(
    val urls: Urls,
    val vapid: Vapid,
    val accounts: Accounts,
    val statuses: Statuses,
    @SerializedName("media_attachments") val mediaAttachments: MediaAttachments,
    val polls: Polls,
    val translation: Translation?,
) {

    data class Urls(val streaming: String)

    data class Vapid(@SerializedName("public_key") val publicKey: String)

    data class Accounts(
        @SerializedName("max_featured_tags") val maxFeaturedTags: Int,
        @SerializedName("max_pinned_statuses") val maxPinnedStatuses: Int?,
    )

    data class Statuses(
        @SerializedName("max_characters") val maxCharacters: Int,
        @SerializedName("max_media_attachments") val maxMediaAttachments: Int,
        @SerializedName("characters_reserved_per_url") val charactersReservedPerUrl: Int,
    )

    data class MediaAttachments(
        @SerializedName("supported_mime_types") val supportedMimeTypes: List<String>,
        @SerializedName("image_size_limit") val imageSizeLimit: Int,
        @SerializedName("image_matrix_limit") val imageMatrixLimit: Int,
        @SerializedName("video_size_limit") val videoSizeLimit: Int,
        @SerializedName("video_frame_rate_limit") val videoFrameRateLimit: Int,
        @SerializedName("video_matrix_limit") val videoMatrixLimit: Int,
    )

    data class Polls(
        @SerializedName("max_options") val maxOptions: Int,
        @SerializedName("max_characters_per_option") val maxCharactersPerOption: Int,
        @SerializedName("min_expiration") val minExpiration: Int,
        @SerializedName("max_expiration") val maxExpiration: Int,
    )

    data class Translation(
        val enabled: Boolean,
    )
}

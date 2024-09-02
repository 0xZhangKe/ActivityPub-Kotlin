package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubEditStatusEntity(
    val status: String,
    @SerialName("spoiler_text") val spoilerText: String,
    val sensitive: Boolean,
    val language: String?,
    @SerialName("media_ids") val mediaIds: List<String>?,
    @SerialName("media_attributes") val mediaAttributes: List<MediaAttributes>?,
    val poll: ActivityPubPollRequestEntity?,
) {

    @Serializable
    data class MediaAttributes(
        val id: String,
        val description: String?,
        val focus: String? = null,
    )
}

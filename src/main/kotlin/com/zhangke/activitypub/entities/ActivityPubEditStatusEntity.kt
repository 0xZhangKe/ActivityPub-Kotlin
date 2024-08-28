package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubEditStatusEntity(
    val status: String?,
    @SerializedName("spoiler_text") val spoilerText: String?,
    val sensitive: Boolean?,
    val language: String?,
    @SerializedName("media_ids") val mediaIds: List<String>?,
    @SerializedName("media_attributes") val mediaAttributes: List<MediaAttributes>?,
    val poll: ActivityPubPollRequestEntity?,
) {

    data class MediaAttributes(
        val id: String,
        val description: String?,
        val focus: String? = null,
    )
}

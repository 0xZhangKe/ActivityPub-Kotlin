package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubEditStatusEntity(
    val status: String? = null,
    @SerialName("spoiler_text") val spoilerText: String? = null,
    val sensitive: Boolean? = null,
    val language: String? = null,
    @SerialName("media_ids") val mediaIds: List<String>? = null,
    @SerialName("media_attributes") val mediaAttributes: List<MediaAttributes>? = null,
    val poll: ActivityPubPollRequestEntity? = null,
    @SerialName("quote_approval_policy")
    val quoteApprovalPolicy: String? = null,
) {

    @Serializable
    data class MediaAttributes(
        val id: String,
        val description: String? = null,
        val focus: String? = null,
    )
}

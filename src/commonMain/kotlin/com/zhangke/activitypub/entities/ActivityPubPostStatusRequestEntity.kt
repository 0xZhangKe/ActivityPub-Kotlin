package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubPostStatusRequestEntity(
    val status: String? = null,
    @SerialName("media_ids")
    val mediaIds: List<String>? = null,
    val poll: ActivityPubPollRequestEntity? = null,
    @SerialName("in_reply_to_id")
    val replyToId: String? = null,
    @SerialName("sensitive")
    val isSensitive: Boolean? = null,
    @SerialName("spoiler_text")
    val spoilerText: String? = null,
    val visibility: String? = null,
    val language: String? = null,
    @SerialName("scheduled_at")
    val scheduledAt: String? = null,
)

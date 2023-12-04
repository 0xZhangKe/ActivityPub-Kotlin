package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubPostStatusRequestEntity(
    val status: String?,
    @SerializedName("media_ids")
    val mediaIds: List<String>?,
    val poll: ActivityPubPollRequestEntity?,
    @SerializedName("in_reply_to_id")
    val replyToId: String?,
    @SerializedName("sensitive")
    val isSensitive: Boolean?,
    @SerializedName("spoiler_text")
    val spoilerText: String?,
    val visibility: String?,
    val language: String?,
    @SerializedName("scheduled_at")
    val scheduledAt: String?,
)

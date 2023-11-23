package com.zhangke.activitypub.entities

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ActivityPubStatusScheduledEntity(
    val id: String,
    @SerializedName("scheduled_at")
    val scheduledAt: String,
    @SerializedName("media_attachments")
    val mediaAttachments: List<ActivityPubMediaAttachmentEntity>,
    val params: JsonObject,
)

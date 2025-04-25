package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ActivityPubStatusScheduledEntity(
    val id: String,
    @SerialName("scheduled_at")
    val scheduledAt: String = "",
    @SerialName("media_attachments")
    val mediaAttachments: List<ActivityPubMediaAttachmentEntity> = emptyList(),
    val params: JsonElement? = null,
)

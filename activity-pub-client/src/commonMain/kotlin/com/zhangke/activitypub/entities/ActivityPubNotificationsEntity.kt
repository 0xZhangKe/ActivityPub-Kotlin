package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubNotificationsEntity(
    val id: String,
    val type: String,
    @SerialName("created_at")
    val createdAt: String,
    val account: ActivityPubAccountEntity,
    @SerialName("status")
    val status: ActivityPubStatusEntity? = null,
    @SerialName("relationship_severance_event")
    val relationshipSeveranceEvent: ActivityPubRelationshipSeveranceEventEntity? = null,
    @SerialName("group_key")
    val groupKey: String? = null,
    @SerialName("moderation_warning")
    val moderationWarning: ActivityPubAccountWarningEntity? = null,
) {

    companion object Type {

        const val MENTION = "mention"
        const val STATUS = "status"
        const val REBLOG = "reblog"
        const val FOLLOW = "follow"
        const val FOLLOW_REQUEST = "follow_request"
        const val FAVORITE = "favourite"
        const val POLL = "poll"
        const val UPDATE = "update"
        const val SEVERED_RELATIONSHIPS = "severed_relationships"
        const val MODERATION_WARNING = "moderation_warning"
        const val QUOTE = "quote"
        const val QUOTED_UPDATE = "quoted_update"
    }
}

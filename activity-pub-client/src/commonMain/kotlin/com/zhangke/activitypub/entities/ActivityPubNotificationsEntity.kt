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
) {

    companion object Type {

        const val mention = "mention"
        const val status = "status"
        const val reblog = "reblog"
        const val follow = "follow"
        const val followRequest = "follow_request"
        const val favourite = "favourite"
        const val poll = "poll"
        const val update = "update"
        const val severedRelationships = "severed_relationships"
    }
}

package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubNotificationsEntity(
    val id: String,
    val type: String,
    @SerializedName("created_at")
    val createdAt: String,
    val account: ActivityPubAccountEntity,
    @SerializedName("status")
    val status: ActivityPubStatusEntity?,
    @SerializedName("relationship_severance_event")
    val relationshipSeveranceEvent: ActivityPubRelationshipSeveranceEventEntity?,
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

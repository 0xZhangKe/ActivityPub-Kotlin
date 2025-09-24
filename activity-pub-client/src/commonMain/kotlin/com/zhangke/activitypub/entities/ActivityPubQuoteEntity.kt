package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubQuoteEntity(
    val state: String,
    @SerialName("quoted_status")
    val quotedStatus: ActivityPubStatusEntity? = null,
    @SerialName("quoted_status_id")
    val quotedStatusId: String? = null,
) {

    companion object {

        const val STATE_PENDING = "pending"
        const val STATE_ACCEPTED = "accepted"
        const val STATE_REJECTED = "rejected"
        const val STATE_REVOKED = "revoked"
        const val STATE_DELETED = "deleted"
        const val STATE_UNAUTHORIZED = "unauthorized"
    }
}

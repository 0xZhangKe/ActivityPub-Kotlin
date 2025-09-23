package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubAppealEntity(
    val text: String,
    val state: String,
) {

    companion object {

        const val STATE_APPROVED = "approved"
        const val STATE_REJECTED = "rejected"
        const val STATE_PENDING = "pending"
    }
}

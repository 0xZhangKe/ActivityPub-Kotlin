package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ActivityPubQuoteApprovalEntity(
    val automatic: List<String>,
    val manual: List<String>,
    @SerialName("current_user")
    val currentUser: String,
) {

    companion object {
        const val PUBLIC = "public"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
        const val UNSUPPORTED_POLICY = "unsupported_policy"

        const val AUTOMATIC = "automatic"
        const val MANUAL = "manual"
        const val DENIED = "denied"
        const val UNKNOWN = "unknown"
    }
}

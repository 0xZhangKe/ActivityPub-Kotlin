package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://docs.joinmastodon.org/entities/QuoteApproval/
 */
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

        const val CURRENT_USER_AUTOMATIC = "automatic"
        const val CURRENT_USER_MANUAL = "manual"
        const val CURRENT_USER_DENIED = "denied"
        const val CURRENT_USER_UNKNOWN = "unknown"
    }
}

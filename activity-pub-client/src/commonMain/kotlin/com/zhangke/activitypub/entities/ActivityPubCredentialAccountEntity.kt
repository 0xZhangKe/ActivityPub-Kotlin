package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubCredentialAccountEntity(
    /**
     * The default post privacy to be used for new statuses.
     * Enum of:
     * public = Public post
     * unlisted = Unlisted post
     * private = Followers-only post
     * direct = Direct post
     */
    val privacy: String,
    val sensitive: Boolean,
    val language: String? = null,
    val note: String = "",
    val fields: List<ActivityPubField> = emptyList(),
)

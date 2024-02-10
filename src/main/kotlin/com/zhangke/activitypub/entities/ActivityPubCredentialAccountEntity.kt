package com.zhangke.activitypub.entities

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
    val language: String,
    val note: String,
    val fields: List<ActivityPubField>,
)

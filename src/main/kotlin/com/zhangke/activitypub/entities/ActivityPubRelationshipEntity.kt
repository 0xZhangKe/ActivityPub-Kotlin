package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubRelationshipEntity(
    val id: String,
    val following: Boolean,
    @SerializedName("showing_reblogs")
    val showingReblogs: Boolean,
    val notifying: Boolean,
    val languages: List<String>?,
    @SerializedName("followed_by")
    val followedBy: Boolean,
    val blocking: Boolean,
    @SerializedName("blocked_by")
    val blockedBy: Boolean,
    val muting: Boolean,
    @SerializedName("muting_notifications")
    val mutingNotifications: Boolean,
    val requested: Boolean,
    @SerializedName("requested_by")
    val requestedBy: Boolean,
    @SerializedName("domain_blocking")
    val domainBlocking: Boolean,
    val endorsed: Boolean,
    val note: String,
)

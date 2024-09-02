package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubRelationshipEntity(
    val id: String,
    val following: Boolean = false,
    @SerialName("showing_reblogs")
    val showingReblogs: Boolean = false,
    val notifying: Boolean = false,
    val languages: List<String>? = null,
    @SerialName("followed_by")
    val followedBy: Boolean = false,
    val blocking: Boolean = false,
    @SerialName("blocked_by")
    val blockedBy: Boolean = false,
    val muting: Boolean = false,
    @SerialName("muting_notifications")
    val mutingNotifications: Boolean = false,
    val requested: Boolean = false,
    @SerialName("requested_by")
    val requestedBy: Boolean = false,
    @SerialName("domain_blocking")
    val domainBlocking: Boolean = false,
    val endorsed: Boolean = false,
    val note: String = "",
)

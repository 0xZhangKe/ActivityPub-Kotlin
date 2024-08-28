package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by ZhangKe on 2022/12/13.
 */
@Serializable
data class ActivityPubAccountEntity(
    val id: String,
    val username: String,
    val acct: String,
    @SerialName("display_name")
    val displayName: String,
    val locked: Boolean,
    val bot: Boolean,
    val discoverable: Boolean,
    val group: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    val note: String,
    val url: String,
    val avatar: String,
    @SerialName("avatar_static")
    val avatarStatic: String,
    val header: String,
    @SerialName("header_static")
    val headerStatic: String,
    @SerialName("followers_count")
    val followersCount: Int,
    @SerialName("following_count")
    val followingCount: Int,
    @SerialName("statuses_count")
    val statusesCount: Int,
    @SerialName("last_status_at")
    val lastStatusAt: String,
    val fields: List<ActivityPubField> = emptyList(),
    val emojis: List<ActivityPubCustomEmojiEntity> = emptyList(),
    val source: ActivityPubCredentialAccountEntity? = null,
)

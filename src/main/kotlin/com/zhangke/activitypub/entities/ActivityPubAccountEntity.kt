package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by ZhangKe on 2022/12/13.
 */
data class ActivityPubAccountEntity(
    val id: String,
    val username: String,
    val acct: String,
    @SerializedName("display_name")
    val displayName: String,
    val locked: Boolean,
    val bot: Boolean,
    val discoverable: Boolean,
    val group: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    val note: String,
    val url: String,
    val avatar: String,
    @SerializedName("avatar_static")
    val avatarStatic: String,
    val header: String,
    @SerializedName("header_static")
    val headerStatic: String,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("statuses_count")
    val statusesCount: Int,
    @SerializedName("last_status_at")
    val lastStatusAt: String,
    val fields: List<ActivityPubField>,
    val emojis: List<ActivityPubCustomEmojiEntity>,
    val source: ActivityPubCredentialAccountEntity?,
)

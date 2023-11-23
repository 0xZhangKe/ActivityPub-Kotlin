package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubAnnouncementEntity(
    val id: String,
    val content: String,
    @SerializedName("starts_at")
    val startsAt: String? = null,
    @SerializedName("ends_at")
    val endsAt: String? = null,
    val published: Boolean,
    /**
     * Whether the announcement should start and end on dates only instead of datetimes.
     * Will be false if there is no starts_at or ends_at time.
     */
    @SerializedName("all_day")
    val allDay: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    /**
     * Whether the announcement has been read by the current user.
     */
    val read: Boolean = false,
    val mentions: List<Account>,
    val statuses: List<Status>,
    val tags: List<ActivityPubStatusTagEntity>,
    val emojis: List<ActivityPubCustomEmojiEntity>,
    val reactions: List<ActivityPubReactionEntity>,
){

    data class Account(
        val id: String,
        val username: String,
        val url: String,
        val acct: String,
    )

    data class Status(
        val id: String,
        val url: String,

    )
}



package com.zhangke.activitypub.entry

import com.google.gson.annotations.SerializedName

data class ActivityPubPollEntity(
    val id: String,
    /**
     * ISO 8601 Datetime
     */
    @SerializedName("expires_at")
    val expiresAt: String?,
    val expired: Boolean,
    /**
     * Does the poll allow multiple-choice answers?
     */
    val multiple: Boolean,
    /**
     * How many votes have been received.
     */
    @SerializedName("votes_count")
    val votesCount: Int,
    /**
     * How many unique accounts have voted on a multiple-choice poll.
     */
    @SerializedName("voters_count")
    val votersCount: Int,
    val options: List<Option>,
    val emojis: List<ActivityPubCustomEmojiEntity>,
    val voted: Boolean?,
    @SerializedName("own_votes")
    val ownVotes: List<Int>?,
) {

    data class Option(
        val title: String,
        @SerializedName("votes_count")
        val votesCount: Int?,
    )
}
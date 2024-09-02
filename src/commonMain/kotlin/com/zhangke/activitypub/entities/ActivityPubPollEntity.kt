package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubPollEntity(
    val id: String,
    /**
     * ISO 8601 Datetime
     */
    @SerialName("expires_at")
    val expiresAt: String? = null,
    val expired: Boolean = false,
    /**
     * Does the poll allow multiple-choice answers?
     */
    val multiple: Boolean = false,
    /**
     * How many votes have been received.
     */
    @SerialName("votes_count")
    val votesCount: Int = 0,
    /**
     * How many unique accounts have voted on a multiple-choice poll.
     */
    @SerialName("voters_count")
    val votersCount: Int = 0,
    val options: List<Option> = emptyList(),
    val emojis: List<ActivityPubCustomEmojiEntity> = emptyList(),
    val voted: Boolean? = null,
    @SerialName("own_votes")
    val ownVotes: List<Int>? = null,
) {

    @Serializable
    data class Option(
        val title: String = "",
        @SerialName("votes_count")
        val votesCount: Int? = null,
    )
}
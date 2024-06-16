package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by ZhangKe on 2022/12/13.
 * https://docs.joinmastodon.org/entities/Status/
 */
data class ActivityPubStatusEntity(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("in_reply_to_id")
    val inReplyToId: String?,
    @SerializedName("in_reply_to_account_id")
    val inReplyToAccountId: String?,
    val account: ActivityPubAccountEntity,
    @SerializedName("media_attachments")
    val mediaAttachments: List<ActivityPubMediaAttachmentEntity>?,
    val sensitive: Boolean,
    @SerializedName("spoiler_text")
    val spoilerText: String,
    val visibility: String,
    val language: String?,
    val uri: String,
    val url: String?,
    @SerializedName("replies_count")
    val repliesCount: Int,
    @SerializedName("reblogs_count")
    val reblogsCount: Int,
    @SerializedName("favourites_count")
    val favouritesCount: Int,
    val favourited: Boolean?,
    val reblogged: Boolean?,
    val pinned: Boolean?,
    val muted: Boolean?,
    val bookmarked: Boolean?,
    val content: String,
    val emojis: List<ActivityPubCustomEmojiEntity>,
    val reblog: ActivityPubStatusEntity?,
    val tags: List<Tag>,
    val poll: ActivityPubPollEntity?,
    val mentions: List<Mention>,
) {

    data class Mention(
        val id: String,
        val username: String,
        val url: String,
        val acct: String,
    )

    data class Tag(
        val name: String,
        val url: String,
    )
}

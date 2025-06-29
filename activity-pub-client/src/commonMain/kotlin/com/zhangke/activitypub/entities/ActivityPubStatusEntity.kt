package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by ZhangKe on 2022/12/13.
 * https://docs.joinmastodon.org/entities/Status/
 */
@Serializable
data class ActivityPubStatusEntity(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("in_reply_to_id")
    val inReplyToId: String? = null,
    @SerialName("in_reply_to_account_id")
    val inReplyToAccountId: String? = null,
    val account: ActivityPubAccountEntity,
    @SerialName("media_attachments")
    val mediaAttachments: List<ActivityPubMediaAttachmentEntity>? = null,
    val sensitive: Boolean = false,
    @SerialName("spoiler_text")
    val spoilerText: String = "",
    val visibility: String = "",
    val language: String? = null,
    val uri: String = "",
    val url: String? = null,
    @SerialName("replies_count")
    val repliesCount: Int = 0,
    @SerialName("reblogs_count")
    val reblogsCount: Int = 0,
    @SerialName("favourites_count")
    val favouritesCount: Int = 0,
    val favourited: Boolean? = null,
    val reblogged: Boolean? = null,
    val pinned: Boolean? = null,
    val muted: Boolean? = null,
    val bookmarked: Boolean? = null,
    val content: String? = null,
    val emojis: List<ActivityPubCustomEmojiEntity> = emptyList(),
    val reblog: ActivityPubStatusEntity? = null,
    val tags: List<Tag> = emptyList(),
    val poll: ActivityPubPollEntity? = null,
    val mentions: List<Mention> = emptyList(),
    val card: PreviewCard? = null,
    @SerialName("edited_at") val editedAt: String? = null,
    val application: Application? = null,
    val filtered: ActivityPubFilterResultEntity? = null,
) {

    @Serializable
    data class Mention(
        val id: String,
        val username: String = "",
        val url: String = "",
        val acct: String = "",
    )

    @Serializable
    data class Tag(
        val name: String = "",
        val url: String = "",
    )

    @Serializable
    data class PreviewCard(
        val url: String,
        val title: String,
        val description: String = "",
        val type: String = "",
        @SerialName("author_name") val authorName: String = "",
        @SerialName("author_url") val authorUrl: String = "",
        @SerialName("provider_name") val providerName: String = "",
        @SerialName("provider_url") val providerUrl: String = "",
        val html: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val image: String? = null,
        @SerialName("embed_url") val embedUrl: String? = null,
        val blurhash: String? = null,
    ) {

        companion object {

            const val TYPE_LINK = "link"
            const val TYPE_PHOTO = "photo"
            const val TYPE_VIDEO = "video"
            const val TYPE_RICH = "rich"
        }
    }

    @Serializable
    data class Application(
        val name: String,
        val website: String? = null,
    )

    companion object {

        const val VISIBILITY_PUBLIC = "public"
        const val VISIBILITY_UNLISTED = "unlisted"
        const val VISIBILITY_PRIVATE = "private"
        const val VISIBILITY_DIRECT = "direct"
    }
}

package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubCustomEmojiEntity(
    val shortcode: String,
    val url: String,
    @SerialName("static_url")
    val staticUrl: String,
    @SerialName("visible_in_picker")
    val visibleInPicker: Boolean = false,
    val category: String? = null,
)

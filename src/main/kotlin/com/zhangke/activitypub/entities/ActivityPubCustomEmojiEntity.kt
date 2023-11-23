package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubCustomEmojiEntity(
    val shortcode: String,
    val url: String,
    @SerializedName("static_url")
    val staticUrl: String,
    @SerializedName("visible_in_picker")
    val visibleInPicker: Boolean,
    val category: String,
)

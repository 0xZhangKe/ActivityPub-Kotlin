package com.zhangke.activitypub.entry

import com.google.gson.annotations.SerializedName

data class ActivityPubReactionEntity(
    val name: String,
    val count: Int,
    /**
     * If there is a currently authorized user: Have you added this reaction?
     */
    val me: Boolean? = null,
    val url: String? = null,
    @SerializedName("static_url")
    val staticUrl: String? = null,
)

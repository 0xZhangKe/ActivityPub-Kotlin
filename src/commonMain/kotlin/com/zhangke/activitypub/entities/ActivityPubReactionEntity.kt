package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubReactionEntity(
    val name: String,
    val count: Int = 0,
    /**
     * If there is a currently authorized user: Have you added this reaction?
     */
    val me: Boolean? = null,
    val url: String? = null,
    @SerialName("static_url")
    val staticUrl: String? = null,
)

package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubStatusTagEntity(
    val name: String,
    val url: String = "",
)

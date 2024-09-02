package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubUpdateMediaRequestEntity(
    val thumbnail: String? = null,
    val description: String? = null,
    val focus: String? = null,
)

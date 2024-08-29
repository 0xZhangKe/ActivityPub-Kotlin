package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubFilterStatusEntity(
    val id: String,
    @SerialName("status_id") val statusId: String,
)

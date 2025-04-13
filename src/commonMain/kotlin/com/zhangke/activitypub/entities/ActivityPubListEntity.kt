package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubListEntity(
    val id: String,
    val title: String = "",
    @SerialName("replies_policy") val repliesPolicy: String = "",
    val exclusive: Boolean = false,
)

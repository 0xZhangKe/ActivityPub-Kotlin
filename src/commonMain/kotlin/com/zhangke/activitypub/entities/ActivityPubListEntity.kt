package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubListEntity(
    val id: String,
    val title: String = "",
)

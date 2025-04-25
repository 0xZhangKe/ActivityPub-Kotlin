package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubStatusContextEntity (
    val ancestors: List<ActivityPubStatusEntity> = emptyList(),
    val descendants: List<ActivityPubStatusEntity> = emptyList(),
)

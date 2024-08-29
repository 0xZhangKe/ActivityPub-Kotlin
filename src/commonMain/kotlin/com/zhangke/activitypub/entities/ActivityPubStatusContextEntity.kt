package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubStatusContextEntity (
    val ancestors: List<ActivityPubStatusEntity>,
    val descendants: List<ActivityPubStatusEntity>,
)

package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubSearchEntity (
    val accounts: List<ActivityPubAccountEntity> = emptyList(),
    val statuses: List<ActivityPubStatusEntity> = emptyList(),
    val hashtags: List<ActivityPubTagEntity> = emptyList(),
)

package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubSearchEntity (
    val accounts: List<ActivityPubAccountEntity>,
    val statuses: List<ActivityPubStatusEntity>,
    val hashtags: List<ActivityPubTagEntity>,
)

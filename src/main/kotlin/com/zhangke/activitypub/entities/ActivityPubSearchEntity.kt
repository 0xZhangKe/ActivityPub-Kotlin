package com.zhangke.activitypub.entities

data class ActivityPubSearchEntity (
    val accounts: List<ActivityPubAccountEntity>,
    val statuses: List<ActivityPubStatusEntity>,
    val hashtags: List<ActivityPubTagEntity>,
)

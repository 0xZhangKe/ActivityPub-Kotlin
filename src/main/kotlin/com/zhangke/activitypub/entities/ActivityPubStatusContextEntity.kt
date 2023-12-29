package com.zhangke.activitypub.entities

data class ActivityPubStatusContextEntity (
    val ancestors: List<ActivityPubStatusEntity>,
    val descendants: List<ActivityPubStatusEntity>,
)

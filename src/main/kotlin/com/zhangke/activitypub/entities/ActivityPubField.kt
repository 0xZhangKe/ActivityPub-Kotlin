package com.zhangke.activitypub.entities

data class ActivityPubField (
    val name: String,
    val value: String,
    val verifiedAt: String? = null,
)

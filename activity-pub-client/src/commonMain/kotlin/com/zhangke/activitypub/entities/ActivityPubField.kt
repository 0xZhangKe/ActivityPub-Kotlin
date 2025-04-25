package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubField (
    val name: String,
    val value: String = "",
    val verifiedAt: String? = null,
)

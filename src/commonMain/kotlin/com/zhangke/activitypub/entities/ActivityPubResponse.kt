package com.zhangke.activitypub.entities

data class ActivityPubResponse<T>(
    val code: Int,
    val response: T,
)

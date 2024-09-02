package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

/**
 * Created by ZhangKe on 2022/12/3.
 */
@Serializable
data class ActivityPubErrorEntry(
    val error: String
)
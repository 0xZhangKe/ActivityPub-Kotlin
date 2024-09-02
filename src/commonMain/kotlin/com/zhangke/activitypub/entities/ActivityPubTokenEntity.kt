package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by ZhangKe on 2022/12/14.
 */
@Serializable
data class ActivityPubTokenEntity(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    val scope: String,
    @SerialName("created_at")
    val createdAt: Long,
)

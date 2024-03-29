package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by ZhangKe on 2022/12/14.
 */
data class ActivityPubTokenEntity(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    val scope: String,
    @SerializedName("created_at")
    val createdAt: String,
)

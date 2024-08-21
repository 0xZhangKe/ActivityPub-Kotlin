package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubFilterStatusEntity(
    val id: String,
    @SerializedName("status_id") val statusId: String,
)

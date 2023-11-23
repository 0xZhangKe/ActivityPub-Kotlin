package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubPollRequestEntity(
    val options: List<String>,
    @SerializedName("expires_in")
    val expiresIn: Long,
    val multiple: Boolean?,
    @SerializedName("hide_totals")
    val hideTotals: Boolean?,
)

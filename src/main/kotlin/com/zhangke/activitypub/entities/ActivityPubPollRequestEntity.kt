package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubPollRequestEntity(
    val options: List<String>,
    @SerialName("expires_in")
    val expiresIn: Long,
    val multiple: Boolean? = null,
    @SerialName("hide_totals")
    val hideTotals: Boolean? = null,
)

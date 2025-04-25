package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubPollRequestEntity(
    val options: List<String> = emptyList(),
    @SerialName("expires_in")
    val expiresIn: Long = 0,
    val multiple: Boolean? = null,
    @SerialName("hide_totals")
    val hideTotals: Boolean? = null,
)

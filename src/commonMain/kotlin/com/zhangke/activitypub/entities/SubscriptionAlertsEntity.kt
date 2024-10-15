package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SubscriptionAlertsEntity(
    val mention: Boolean = false,
    val status: Boolean = false,
    val reblog: Boolean = false,
    val follow: Boolean = false,
    @SerialName("follow_request")
    val followRequest: Boolean = false,
    val favourite: Boolean = false,
    val poll: Boolean = false,
    val update: Boolean = false,
)

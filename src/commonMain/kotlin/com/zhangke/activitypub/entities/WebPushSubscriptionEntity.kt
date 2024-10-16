package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebPushSubscriptionEntity(
    val id: Int,
    val endpoint: String,
    val alerts: SubscriptionAlertsEntity,
    @SerialName("server_key")
    val serverKey: String,
    val policy: String,
)

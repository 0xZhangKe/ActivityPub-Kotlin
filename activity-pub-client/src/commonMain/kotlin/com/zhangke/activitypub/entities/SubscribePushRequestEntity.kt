package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class SubscribePushRequestEntity(
    val subscription: Subscription,
    val data: Data? = null,
) {

    @Serializable
    data class Subscription(
        val endpoint: String,
        val keys: Keys,
    ) {

        @Serializable
        data class Keys(
            val p256dh: String,
            val auth: String,
        )
    }

    @Serializable
    data class Data(
        val alerts: SubscriptionAlertsEntity? = null,
        val policy: String? = null,
    )
}

package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubMarkersEntity(
    val notifications: Marker? = null,
    val home: Marker? = null,
) {

    @Serializable
    data class Marker(
        @SerialName("last_read_id")
        val lastReadId: String,
        val version: Int,
        @SerialName("updated_at")
        val updatedAt: String,
    )
}

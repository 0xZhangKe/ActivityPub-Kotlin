package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName

data class ActivityPubMarkersEntity(
    val notifications: Marker?,
    val home: Marker?,
) {

    data class Marker(
        @SerialName("last_read_id")
        val lastReadId: String,
        val version: Int,
        @SerialName("updated_at")
        val updatedAt: String,
    )
}

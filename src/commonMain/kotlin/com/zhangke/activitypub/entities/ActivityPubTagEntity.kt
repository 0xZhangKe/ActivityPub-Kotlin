package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubTagEntity(
    val name: String,
    val url: String,
    val history: List<History>,
    val following: Boolean = false,
) {

    @Serializable
    data class History(
        val day: Long,
        val uses: Int,
        val accounts: Int,
    )
}

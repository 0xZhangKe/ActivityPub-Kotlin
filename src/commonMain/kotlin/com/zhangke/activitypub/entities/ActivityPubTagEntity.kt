package com.zhangke.activitypub.entities

import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubTagEntity(
    val name: String,
    val url: String,
    val history: List<History> = emptyList(),
    val following: Boolean = false,
) {

    @Serializable
    data class History(
        val day: Long = 0,
        val uses: Int = 0,
        val accounts: Int = 0,
    )
}

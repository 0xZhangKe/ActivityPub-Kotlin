package com.zhangke.activitypub.entities

data class ActivityPubTagEntity(
    val name: String,
    val url: String,
    val history: List<History>,
    val following: Boolean = false,
) {

    data class History(
        val day: Long,
        val uses: Int,
        val accounts: Int,
    )
}

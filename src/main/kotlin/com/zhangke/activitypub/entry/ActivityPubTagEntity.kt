package com.zhangke.activitypub.entry

data class ActivityPubTagEntity(
    val name: String,
    val url: String,
    val history: List<History>,
    val following: Boolean = false,
) {

    data class History(
        val day: String,
        val uses: Int,
        val accounts: Int,
    )
}

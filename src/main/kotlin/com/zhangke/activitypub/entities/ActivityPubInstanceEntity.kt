package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubInstanceEntity(
    val domain: String,
    val title: String,
    val version: String,
    @SerializedName("source_url") val sourceUrl: String?,
    val description: String,
    val usage: Usage,
    val thumbnail: Thumbnail,
    val languages: List<String>,
    val rules: List<Rule>,
    val contact: Contact,
) {

    data class Usage(val users: Users) {

        data class Users(
            @SerializedName("active_month") val activeMonth: Int
        )
    }

    data class Thumbnail(val url: String)

    data class Rule(
        val id: String,
        val text: String,
    )

    data class Contact(
        val email: String,
        val account: ActivityPubAccountEntity,
    )
}

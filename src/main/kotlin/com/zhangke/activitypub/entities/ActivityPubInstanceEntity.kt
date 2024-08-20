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
    val languages: List<String>?,
    val rules: List<Rule>,
    val contact: Contact?,
    val configuration: ActivityPubInstanceConfigurationEntity,
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

internal data class ActivityPubV1InstanceEntity(
    val uri: String,
    val title: String,
    val version: String,
    @SerializedName("source_url") val sourceUrl: String?,
    val description: String,
    val usage: ActivityPubInstanceEntity.Usage?,
    val thumbnail: String,
    val languages: List<String>?,
    val stats: Stats,
    val rules: List<ActivityPubInstanceEntity.Rule>?,
    @SerializedName("contact_account") val contactAccount: ActivityPubAccountEntity?,
    val configuration: ActivityPubInstanceConfigurationEntity,
) {

    data class Stats(
        @SerializedName("user_count") val userCount: Int,
        @SerializedName("status_count") val statusCount: Int,
        @SerializedName("domain_count") val domainCount: Int,
    ) {

        fun toUsage(): ActivityPubInstanceEntity.Usage {
            return ActivityPubInstanceEntity.Usage(
                users = ActivityPubInstanceEntity.Usage.Users(userCount)
            )
        }
    }

    fun toInstanceEntity(): ActivityPubInstanceEntity {
        return ActivityPubInstanceEntity(
            domain = uri,
            title = title,
            version = version,
            sourceUrl = sourceUrl,
            description = description,
            usage = usage ?: stats.toUsage(),
            thumbnail = ActivityPubInstanceEntity.Thumbnail(thumbnail),
            languages = languages,
            rules = rules ?: emptyList(),
            contact = contactAccount?.let {
                ActivityPubInstanceEntity.Contact(
                    email = "",
                    account = it,
                )
            },
            configuration = configuration,
        )
    }
}

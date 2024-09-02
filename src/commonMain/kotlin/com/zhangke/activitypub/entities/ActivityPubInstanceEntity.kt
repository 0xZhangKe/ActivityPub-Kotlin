package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubInstanceEntity(
    val domain: String,
    val title: String,
    val version: String,
    @SerialName("source_url") val sourceUrl: String? = null,
    val description: String,
    val usage: Usage,
    val thumbnail: Thumbnail,
    val languages: List<String>? = null,
    val rules: List<Rule> = emptyList(),
    val contact: Contact? = null,
    val configuration: ActivityPubInstanceConfigurationEntity,
) {

    @Serializable
    data class Usage(val users: Users) {

        @Serializable
        data class Users(
            @SerialName("active_month") val activeMonth: Int
        )
    }

    @Serializable
    data class Thumbnail(val url: String)

    @Serializable
    data class Rule(
        val id: String,
        val text: String,
    )

    @Serializable
    data class Contact(
        val email: String,
        val account: ActivityPubAccountEntity,
    )

}

@Serializable
internal data class ActivityPubV1InstanceEntity(
    val uri: String,
    val title: String,
    val version: String,
    @SerialName("source_url") val sourceUrl: String? = null,
    val description: String,
    val usage: ActivityPubInstanceEntity.Usage? = null,
    val thumbnail: String = "",
    val languages: List<String>? = null,
    val stats: Stats,
    val rules: List<ActivityPubInstanceEntity.Rule>? = null,
    @SerialName("contact_account") val contactAccount: ActivityPubAccountEntity? = null,
    val configuration: ActivityPubInstanceConfigurationEntity,
) {

    @Serializable
    data class Stats(
        @SerialName("user_count") val userCount: Int,
        @SerialName("status_count") val statusCount: Int,
        @SerialName("domain_count") val domainCount: Int,
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

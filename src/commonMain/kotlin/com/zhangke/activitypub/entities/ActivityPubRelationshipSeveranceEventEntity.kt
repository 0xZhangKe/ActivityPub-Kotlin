package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubRelationshipSeveranceEventEntity(
    val id: String,
    val type: String,
    val purged: Boolean,
    @SerialName("target_name")
    val targetName: String,
    @SerialName("relationships_count")
    val relationshipsCount: Int? = null,
    @SerialName("created_at")
    val createdAt: String,
) {

    companion object Type {

        const val domainBlock = "domain_block"
        const val userDomainBlock = "user_domain_block"
        const val accountSuspension = "account_suspension"
    }
}

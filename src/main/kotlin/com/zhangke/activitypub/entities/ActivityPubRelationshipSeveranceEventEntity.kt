package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubRelationshipSeveranceEventEntity(
    val id: String,
    val type: String,
    val purged: Boolean,
    @SerializedName("target_name")
    val targetName: String,
    @SerializedName("relationships_count")
    val relationshipsCount: Int?,
    @SerializedName("created_at")
    val createdAt: String,
) {

    companion object Type {

        const val domainBlock = "domain_block"
        const val userDomainBlock = "user_domain_block"
        const val accountSuspension = "account_suspension"
    }
}

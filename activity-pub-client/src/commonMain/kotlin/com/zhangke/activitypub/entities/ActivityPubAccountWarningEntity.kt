package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubAccountWarningEntity(
    val id: String,
    val action: String,
    val text: String,
    @SerialName("status_ids")
    val statusIds: List<String>? = null,
    @SerialName("target_account")
    val targetAccount: ActivityPubAccountEntity,
    val appeal: ActivityPubAppealEntity? = null,
    @SerialName("created_at")
    val createdAt: String,
)

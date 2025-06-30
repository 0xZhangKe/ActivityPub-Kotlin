package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubFilterEntity(
    val id: String,
    val title: String,
    val context: List<String>,
    @SerialName("expires_at") val expiresAt: String? = null,
    /**
     * enum of: warn,keywords
     */
    @SerialName("filter_action") val filterAction: String = "",
    val keywords: List<ActivityPubFilterKeywordEntity>? = null,
    val statuses: List<ActivityPubFilterStatusEntity> = emptyList(),
) {

    companion object {

        const val FILTER_ACTION_WARN = "warn"
        const val FILTER_ACTION_KEYWORDS = "hide"
        const val FILTER_ACTION_BLUR = "blur"

        const val CONTEXT_HOME = "home"
        const val CONTEXT_NOTIFICATIONS = "notifications"
        const val CONTEXT_PUBLIC = "public"
        const val CONTEXT_THREAD = "thread"
        const val CONTEXT_ACCOUNT = "account"
    }
}

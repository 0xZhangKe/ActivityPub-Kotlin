package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubFilterEntity(
    val id: String,
    val title: String,
    val context: List<String>,
    @SerializedName("expires_at") val expiresAt: String?,
    /**
     * enum of: warn,keywords
     */
    @SerializedName("filter_action") val filterAction: String,
    val keywords: List<ActivityPubFilterKeywordEntity>?,
    val statuses: List<ActivityPubFilterStatusEntity>,
) {

    companion object {

        const val FILTER_ACTION_WARN = "warn"
        const val FILTER_ACTION_KEYWORDS = "keywords"

        const val CONTEXT_HOME = "home"
        const val CONTEXT_NOTIFICATIONS = "notifications"
        const val CONTEXT_PUBLIC = "public"
        const val CONTEXT_THREAD = "thread"
        const val CONTEXT_ACCOUNT = "account"
    }
}

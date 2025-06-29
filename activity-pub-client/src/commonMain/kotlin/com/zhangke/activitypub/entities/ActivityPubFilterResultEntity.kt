package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubFilterResultEntity(
    val filter: ActivityPubFilterEntity,
    @SerialName("keyword_matches") val keywordMatches: List<String>,
    @SerialName("status_matches") val statusMatches: List<String>,
)

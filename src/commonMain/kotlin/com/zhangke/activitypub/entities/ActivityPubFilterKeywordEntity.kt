package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubFilterKeywordEntity(
    val id: String,
    val keyword: String,
    @SerialName("whole_word") val wholeWord: Boolean = false,
)

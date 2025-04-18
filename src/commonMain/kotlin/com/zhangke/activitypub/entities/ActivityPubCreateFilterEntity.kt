package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubCreateFilterEntity(
    val title: String,
    val context: List<String>,
    @SerialName("filter_action") val filterAction: String = "",
    @SerialName("expires_in") val expiresIn: Int? = null,
    @SerialName("keywords_attributes") val keywordsAttributes: List<KeywordAttribute> = emptyList(),
) {

    @Serializable
    data class KeywordAttribute(
        val keyword: String = "",
        @SerialName("whole_word") val wholeWord: Boolean = false,
        val id: String? = null,
        @SerialName("_destroy") val destroy: Boolean? = null,
    )
}

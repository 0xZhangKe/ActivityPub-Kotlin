package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubCreateFilterEntity(
    val title: String,
    val context: List<String>,
    @SerializedName("filter_action") val filterAction: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("keywords_attributes") val keywordsAttributes: List<KeywordAttribute>,
) {

    data class KeywordAttribute(
        val keyword: String,
        @SerializedName("whole_word") val wholeWord: Boolean,
    )
}

package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubFilterKeywordEntity(
    val id: String,
    val keyword: String,
    @SerializedName("whole_word") val wholeWord: Boolean,
)

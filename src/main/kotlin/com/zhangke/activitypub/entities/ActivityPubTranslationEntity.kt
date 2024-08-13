package com.zhangke.activitypub.entities

import com.google.gson.annotations.SerializedName

data class ActivityPubTranslationEntity(
    val content: String,
    @SerializedName("spoiler_text") val spoilerText: String,
    val poll: Poll?,
    @SerializedName("Attachment") val mediaAttachments: Attachment,
    @SerializedName("detected_source_language") val detectedSourceLanguage: String,
    val provider: String,
) {

    data class Poll(
        val id: String,
        val options: List<Option>,
    ) {

        data class Option(
            val title: String,
        )
    }

    data class Attachment(
        val id: String,
        val description: String,
    )
}

package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubTranslationEntity(
    val content: String = "",
    @SerialName("spoiler_text") val spoilerText: String = "",
    val poll: Poll? = null,
    @SerialName("Attachment") val mediaAttachments: List<Attachment>? = null,
    @SerialName("detected_source_language") val detectedSourceLanguage: String = "",
    val provider: String = "",
) {

    @Serializable
    data class Poll(
        val id: String,
        val options: List<Option> = emptyList(),
    ) {

        @Serializable
        data class Option(
            val title: String = "",
        )
    }

    @Serializable
    data class Attachment(
        val id: String,
        val description: String = "",
    )
}

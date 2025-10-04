package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubPreferencesEntity(
    @SerialName("posting:default:visibility")
    val postingDefaultVisibility: String,
    @SerialName("posting:default:sensitive")
    val postingDefaultSensitive: Boolean,
    @SerialName("posting:default:language")
    val postingDefaultLanguage: String? = null,
    @SerialName("posting:default:quote_policy")
    val postingDefaultQuotePolicy: String? = null,
    @SerialName("reading:expand:media")
    val readingExpandMedia: String,
    @SerialName("reading:expand:spoilers")
    val readingExpandSpoilers: Boolean,
    @SerialName("reading:autoplay:gifs")
    val readingAutoplayGifs: Boolean? = null,
)

package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubMediaMetaEntity(
    val length: String? = null,
    val duration: Double? = null,
    val fps: Int? = null,
    val size: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val aspect: Float? = null,
    @SerialName("audio_encode")
    val audioEncode: String? = null,
    @SerialName("audio_bitrate")
    val audioBitrate: String? = null,
    @SerialName("audio_channels")
    val audioChannels: String? = null,
    val original: LayoutMeta? = null,
    val small: LayoutMeta? = null,
    val focus: FocusMeta? = null,
) {

    @Serializable
    data class LayoutMeta(
        val width: Int? = null,
        val height: Int? = null,
        val size: String? = null,
        val aspect: Float? = null,
        @SerialName("frame_rate")
        val frameRate: String? = null,
        val bitrate: Int? = null,
        val duration: Double? = null,
    )

    @Serializable
    data class FocusMeta(
        val x: Float? = null,
        val y: Float? = null,
    )
}

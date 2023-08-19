package com.zhangke.activitypub.entry

import com.google.gson.annotations.SerializedName

data class ActivityPubMediaMetaEntity(
    val length: String?,
    val duration: Double?,
    val fps: Int?,
    val size: String?,
    val width: Int?,
    val height: Int?,
    val aspect: Float?,
    @SerializedName("audio_encode")
    val audioEncode: String?,
    @SerializedName("audio_bitrate")
    val audioBitrate: String?,
    @SerializedName("audio_channels")
    val audioChannels: String?,
    val original: LayoutMeta?,
    val small: LayoutMeta?,
    val focus: FocusMeta?,
) {

    data class LayoutMeta(
        val width: Int?,
        val height: Int?,
        val size: String?,
        val aspect: Float?,
        @SerializedName("frame_rate")
        val frameRate: String?,
        val bitrate: Int?,
        val duration: Double?,
    )

    data class FocusMeta(
        val x: Float?,
        val y: Float?,
    )
}

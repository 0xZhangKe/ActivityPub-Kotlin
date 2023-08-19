package com.zhangke.activitypub.entry

import com.google.gson.annotations.SerializedName
import com.zhangke.activitypub.entry.ActivityPubMediaAttachmentEntity.Companion.TYPE_AUDIO
import com.zhangke.activitypub.entry.ActivityPubMediaAttachmentEntity.Companion.TYPE_GIFV
import com.zhangke.activitypub.entry.ActivityPubMediaAttachmentEntity.Companion.TYPE_IMAGE
import com.zhangke.activitypub.entry.ActivityPubMediaAttachmentEntity.Companion.TYPE_UNKNOWN
import com.zhangke.activitypub.entry.ActivityPubMediaAttachmentEntity.Companion.TYPE_VIDEO

data class ActivityPubMediaAttachmentEntity(
    val id: String,
    /**
     * Media type.
     * enum of [TYPE_UNKNOWN], [TYPE_IMAGE], [TYPE_GIFV], [TYPE_VIDEO], [TYPE_AUDIO]
     */
    val type: String,
    val url: String,
    @SerializedName("preview_url")
    val previewUrl: String,
    @SerializedName("remote_url")
    val remoteUrl: String? = null,
    val description: String,
    val blurhash: String,
    val meta: ActivityPubMediaMetaEntity?,
) {

    companion object {

        const val TYPE_UNKNOWN = "unknown"
        const val TYPE_IMAGE = "image"
        const val TYPE_GIFV = "gifv"
        const val TYPE_VIDEO = "video"
        const val TYPE_AUDIO = "audio"
    }
}
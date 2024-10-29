package com.zhangke.activitypub.entities

import kotlinx.serialization.SerialName
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity.Companion.TYPE_AUDIO
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity.Companion.TYPE_GIFV
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity.Companion.TYPE_IMAGE
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity.Companion.TYPE_UNKNOWN
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity.Companion.TYPE_VIDEO
import kotlinx.serialization.Serializable

@Serializable
data class ActivityPubMediaAttachmentEntity(
    val id: String,
    /**
     * Media type.
     * enum of [TYPE_UNKNOWN], [TYPE_IMAGE], [TYPE_GIFV], [TYPE_VIDEO], [TYPE_AUDIO]
     */
    val type: String,
    val url: String? = null,
    @SerialName("preview_url")
    val previewUrl: String = "",
    @SerialName("remote_url")
    val remoteUrl: String? = null,
    val description: String? = null,
    val blurhash: String? = null,
    val meta: ActivityPubMediaMetaEntity? = null,
) {

    companion object {

        const val TYPE_UNKNOWN = "unknown"
        const val TYPE_IMAGE = "image"
        const val TYPE_GIFV = "gifv"
        const val TYPE_VIDEO = "video"
        const val TYPE_AUDIO = "audio"
    }
}

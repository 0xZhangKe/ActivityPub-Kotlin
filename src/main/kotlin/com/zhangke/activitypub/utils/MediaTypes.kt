package com.zhangke.activitypub.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

object MediaTypes {

    val image: MediaType get() = "image/*".toMediaType()

    val video: MediaType get() = "video/*".toMediaType()

    val text: MediaType get() = "text/plain".toMediaType()
}
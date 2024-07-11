package com.zhangke.activitypub.utils

internal object ActivityPubHeaders {

    const val AUTH_KEY = "Authorization"

    fun buildAuthTokenHeader(accessToken: String): String {
        return "Bearer $accessToken"
    }
}
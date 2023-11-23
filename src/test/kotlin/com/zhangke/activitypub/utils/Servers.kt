package com.zhangke.activitypub.utils

import com.zhangke.activitypub.ActivityPubClient

/**
 * Created by ZhangKe on 2022/12/3.
 */

private const val MASTODON = "https://mastodon.social"
private const val CMX = "https://m.cmx.im"
private const val ANDROID = "https://androiddev.social"

internal fun newMastodonClient(): ActivityPubClient {
    return newTestActivityPubClient(MASTODON)
}

internal fun newCmxClient(): ActivityPubClient {
    return newTestActivityPubClient(CMX)
}

internal fun newAndroidClient(): ActivityPubClient {
    return newTestActivityPubClient(ANDROID)
}

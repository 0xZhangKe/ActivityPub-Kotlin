package com.zhangke.activitypub.utils

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.api.testApplication

fun newTestActivityPubClient(baseUrl: String): ActivityPubClient {
    return ActivityPubClient(
        application = testApplication,
        retrofit = newTestRetrofit(baseUrl),
        gson = testGlobalGson,
        redirectUrl = "",
        tokenProvider = {
            null
        },
        onAuthorizeFailed = { url: String, client: ActivityPubClient ->

        }
    )
}
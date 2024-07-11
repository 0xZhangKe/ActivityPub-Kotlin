package com.zhangke.activitypub.utils

import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class ActivityPubHeaderInterceptor(
    val tokenProvider: suspend () -> ActivityPubTokenEntity?,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val setAuthHeader = request.header(ActivityPubHeaders.AUTH_KEY)
        if (!setAuthHeader.isNullOrEmpty()) {
            return chain.proceed(request)
        }
        val token = runBlocking {
            tokenProvider()
        }
        if (token == null) {
            return chain.proceed(request)
        }
        val authorization = ActivityPubHeaders.buildAuthTokenHeader(token.accessToken)
        val newRequest = request.newBuilder()
            .addHeader(ActivityPubHeaders.AUTH_KEY, authorization)
            .build()
        return chain.proceed(newRequest)
    }
}

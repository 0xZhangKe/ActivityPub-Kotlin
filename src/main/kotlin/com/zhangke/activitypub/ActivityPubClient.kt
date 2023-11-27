package com.zhangke.activitypub

import com.google.gson.Gson
import com.zhangke.activitypub.api.AccountsRepo
import com.zhangke.activitypub.api.InstanceRepo
import com.zhangke.activitypub.api.MediaRepo
import com.zhangke.activitypub.api.OAuthRepo
import com.zhangke.activitypub.api.StatusRepo
import com.zhangke.activitypub.api.TimelinesRepo
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import com.zhangke.activitypub.utils.ResultCallAdapterFactory
import retrofit2.Retrofit

/**
 * Created by ZhangKe on 2022/12/3.
 */
class ActivityPubClient(
    val application: ActivityPubApplication,
    retrofit: Retrofit,
    gson: Gson,
    private val redirectUrl: String,
    val tokenProvider: suspend () -> ActivityPubTokenEntity?,
    val onAuthorizeFailed: suspend (url: String, client: ActivityPubClient) -> Unit
) {

    internal val retrofit: Retrofit = retrofit.newBuilder()
        .addCallAdapterFactory(ResultCallAdapterFactory(gson))
        .build()

    val oauthRepo: OAuthRepo by lazy { OAuthRepo(this) }

    val instanceRepo: InstanceRepo by lazy { InstanceRepo(this) }

    val accountRepo: AccountsRepo by lazy { AccountsRepo(this) }

    val timelinesRepo: TimelinesRepo by lazy { TimelinesRepo(this) }

    val statusRepo: StatusRepo by lazy { StatusRepo(this) }

    val mediaRepo: MediaRepo by lazy { MediaRepo(this) }

    val baseUrl: String = buildBaseUrl(application.host)

    fun buildOAuthUrl(): String {
        ///oauth/authorize?response_type=code&client_id=&redirect_uri=&scope=read+write+follow+push
        val baseUrl = baseUrl.removeSuffix("/")
        return "${baseUrl}/oauth/authorize" +
                "?response_type=code" +
                "&client_id=${application.clientId}" +
                "&redirect_uri=$redirectUrl" +
                "&scope=read+write+follow+push"
    }

    private fun buildBaseUrl(host: String): String {
        return "https://$host"
    }
}
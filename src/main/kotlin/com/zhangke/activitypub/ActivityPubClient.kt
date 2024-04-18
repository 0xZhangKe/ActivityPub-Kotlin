package com.zhangke.activitypub

import com.google.gson.Gson
import com.zhangke.activitypub.api.AccountsRepo
import com.zhangke.activitypub.api.AppsRepo
import com.zhangke.activitypub.api.CustomEmojiRepo
import com.zhangke.activitypub.api.InstanceRepo
import com.zhangke.activitypub.api.MediaRepo
import com.zhangke.activitypub.api.NotificationsRepo
import com.zhangke.activitypub.api.OAuthRepo
import com.zhangke.activitypub.api.SearchRepo
import com.zhangke.activitypub.api.StatusRepo
import com.zhangke.activitypub.api.TimelinesRepo
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import com.zhangke.activitypub.utils.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ZhangKe on 2022/12/3.
 */
class ActivityPubClient(
    val baseUrl: String,
    val httpClient: OkHttpClient,
    val gson: Gson,
    val tokenProvider: suspend () -> ActivityPubTokenEntity?,
    val onAuthorizeFailed: suspend () -> Unit
) {

    internal val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ResultCallAdapterFactory(gson))
        .client(httpClient)
        .build()

    val oauthRepo: OAuthRepo by lazy { OAuthRepo(this) }

    val appsRepo: AppsRepo by lazy { AppsRepo(retrofit) }

    val instanceRepo: InstanceRepo by lazy { InstanceRepo(this) }

    val accountRepo: AccountsRepo by lazy { AccountsRepo(this) }

    val timelinesRepo: TimelinesRepo by lazy { TimelinesRepo(this) }

    val statusRepo: StatusRepo by lazy { StatusRepo(this) }

    val mediaRepo: MediaRepo by lazy { MediaRepo(this) }

    val emojiRepo: CustomEmojiRepo by lazy { CustomEmojiRepo(this) }

    val notificationsRepo: NotificationsRepo by lazy { NotificationsRepo(this) }

    val searchRepo: SearchRepo by lazy { SearchRepo(this) }
}

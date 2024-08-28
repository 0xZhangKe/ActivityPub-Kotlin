package com.zhangke.activitypub

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
import com.zhangke.activitypub.entities.ActivityPubErrorEntry
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import com.zhangke.activitypub.exception.handleErrorResponseToException
import com.zhangke.activitypub.utils.AuthorizationPlugin
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

/**
 * Created by ZhangKe on 2022/12/3.
 */
class ActivityPubClient(
    baseUrl: String,
    private val httpClient: OkHttpClient,
    val json: Json,
    private val tokenProvider: suspend () -> ActivityPubTokenEntity?,
    onAuthorizeFailed: () -> Unit,
) {

    internal val ktorfit: Ktorfit by lazy {
        Ktorfit.Builder()
            .baseUrl(baseUrl)
            .converterFactories(
                ResponseConverterFactory(),
            )
            .httpClient(createHttpClient())
            .build()
    }

    private fun createHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                preconfigured = httpClient
            }
            install(ContentNegotiation) {
                json(json)
            }
            install(AuthorizationPlugin) {
                tokenProvider = this@ActivityPubClient.tokenProvider
            }
            HttpResponseValidator {
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        throw handleErrorResponseToException(
                            json = json,
                            response = response,
                        )
                    }
                }
            }
        }
    }

    var apiLevel: ActivityPubInstanceApiLevel = ActivityPubInstanceApiLevel.V2

    val oauthRepo: OAuthRepo by lazy { OAuthRepo(this) }

    val appsRepo: AppsRepo by lazy { AppsRepo(this) }

    val instanceRepo: InstanceRepo by lazy { InstanceRepo(this) }

    val accountRepo: AccountsRepo by lazy { AccountsRepo(this) }

    val timelinesRepo: TimelinesRepo by lazy { TimelinesRepo(this) }

    val statusRepo: StatusRepo by lazy { StatusRepo(this) }

    val mediaRepo: MediaRepo by lazy { MediaRepo(this) }

    val emojiRepo: CustomEmojiRepo by lazy { CustomEmojiRepo(this) }

    val notificationsRepo: NotificationsRepo by lazy { NotificationsRepo(this) }

    val searchRepo: SearchRepo by lazy { SearchRepo(this) }
}

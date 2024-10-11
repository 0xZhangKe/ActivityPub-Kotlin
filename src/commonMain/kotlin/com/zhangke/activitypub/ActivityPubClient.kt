package com.zhangke.activitypub

import com.zhangke.activitypub.api.AccountsRepo
import com.zhangke.activitypub.api.AppsRepo
import com.zhangke.activitypub.api.CustomEmojiRepo
import com.zhangke.activitypub.api.InstanceRepo
import com.zhangke.activitypub.api.MarkersRepo
import com.zhangke.activitypub.api.MediaRepo
import com.zhangke.activitypub.api.NotificationsRepo
import com.zhangke.activitypub.api.OAuthRepo
import com.zhangke.activitypub.api.SearchRepo
import com.zhangke.activitypub.api.StatusRepo
import com.zhangke.activitypub.api.TimelinesRepo
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import com.zhangke.activitypub.exception.handleErrorResponseToException
import com.zhangke.activitypub.utils.AuthorizationPlugin
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by ZhangKe on 2022/12/3.
 */
class ActivityPubClient(
    val baseUrl: String,
    private val engine: HttpClientEngine,
    val json: Json,
    private val tokenProvider: suspend () -> ActivityPubTokenEntity?,
    onAuthorizeFailed: () -> Unit,
) {

    internal val httpClient: HttpClient by lazy {
        createHttpClient()
    }

    internal val ktorfit: Ktorfit by lazy {
        Ktorfit.Builder()
            .baseUrl(baseUrl)
            .converterFactories(
                ResponseConverterFactory(),
            )
            .httpClient(httpClient)
            .build()
    }

    private fun createHttpClient(): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(json)
            }
            install(AuthorizationPlugin) {
                tokenProvider = this@ActivityPubClient.tokenProvider
            }
            install(DefaultRequest){
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            HttpResponseValidator {
                validateResponse { response ->
                    if (!response.status.isSuccess()) {
                        throw handleErrorResponseToException(
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

    val markerRepo: MarkersRepo by lazy { MarkersRepo(this) }
}

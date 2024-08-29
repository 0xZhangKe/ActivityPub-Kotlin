package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.RegisterApplicationEntry
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import io.ktor.http.takeFrom

/**
 * Created by ZhangKe on 2022/12/2.
 */
class AppsRepo(private val client: ActivityPubClient) {

    suspend fun registerApplication(
        clientName: String,
        redirectUris: List<String>,
        scopes: List<String>,
        website: String
    ): Result<RegisterApplicationEntry> {
        return runCatching {
            client.ktorfit.httpClient.post {
                url {
                    takeFrom(client.ktorfit.baseUrl + "/api/v1/apps")
                }
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("client_name", clientName)
                            append("redirect_uris", redirectUris.joinToString(":"))
                            append("scopes", scopes.joinToString(" "))
                            append("website", website)
                        }
                    )
                )
            }.body()
        }
    }

    /**
     * https://docs.joinmastodon.org/api/oauth-scopes/#granular
     */
    object AppScopes {

        const val READ = "read"

        const val WRITE = "write"

        const val FOLLOW = "follow"

        const val PUSH = "push"

        val ALL = listOf(READ, WRITE, FOLLOW, PUSH)
    }
}
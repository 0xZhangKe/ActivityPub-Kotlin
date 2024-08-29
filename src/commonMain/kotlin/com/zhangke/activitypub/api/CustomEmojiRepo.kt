package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubCustomEmojiEntity
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.takeFrom

class CustomEmojiRepo(private val client: ActivityPubClient) {

    suspend fun getCustomEmojis(): Result<List<ActivityPubCustomEmojiEntity>> {
        return runCatching {
            client.ktorfit.httpClient.get {
                url {
                    takeFrom(client.ktorfit.baseUrl + "/api/v1/custom_emojis")
                }
            }.body()
        }
    }
}

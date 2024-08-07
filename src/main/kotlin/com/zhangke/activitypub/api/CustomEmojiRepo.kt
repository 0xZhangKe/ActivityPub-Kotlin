package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubCustomEmojiEntity
import retrofit2.http.GET

private interface CustomEmojiService {

    @GET("/api/v1/custom_emojis")
    suspend fun getCustomEmojis(): Result<List<ActivityPubCustomEmojiEntity>>
}

class CustomEmojiRepo(client: ActivityPubClient) {

    private val api: CustomEmojiService = client.retrofit.create(CustomEmojiService::class.java)

    suspend fun getCustomEmojis(): Result<List<ActivityPubCustomEmojiEntity>> {
        return api.getCustomEmojis()
    }
}

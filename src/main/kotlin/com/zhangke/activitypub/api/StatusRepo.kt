package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubPollRequestEntity
import com.zhangke.activitypub.entities.ActivityPubPostStatusRequestEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubStatusVisibilityEntity
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private interface StatusService {

    @POST("/api/v1/statuses")
    suspend fun postStatus(
        @Header("Authorization") authorization: String,
        @Body requestBody: ActivityPubPostStatusRequestEntity,
    ): Result<ActivityPubStatusEntity>
}

class StatusRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(StatusService::class.java)

    suspend fun postStatus(
        status: String?,
        mediaIds: List<String>? = null,
        poll: ActivityPubPollRequestEntity? = null,
        replyToId: String? = null,
        sensitive: Boolean? = null,
        spoilerText: String? = null,
        visibility: ActivityPubStatusVisibilityEntity? = null,
        language: String? = null,
        scheduledAt: String? = null,
    ): Result<ActivityPubStatusEntity> {
        val requestBody = ActivityPubPostStatusRequestEntity(
            status = status,
            mediaIds = mediaIds,
            poll = poll,
            replyToId = replyToId,
            isSensitive = sensitive,
            spoilerText = spoilerText,
            visibility = visibility?.code,
            language = language,
            scheduledAt = scheduledAt,
        )
        return api.postStatus(
            authorization = getAuthorizationHeader(),
            requestBody = requestBody,
        )
    }
}

package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubPollRequestEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubStatusVisibilityEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private interface StatusService {

    @FormUrlEncoded
    @POST("/api/v2/statuses")
    suspend fun postStatus(
        @Field("status") status: String?,
        @Field("media_ids") mediaIds: List<String>?,
        @Field("poll") poll: ActivityPubPollRequestEntity?,
        @Field("in_reply_to_id") replyToId: String?,
        @Field("sensitive") isSensitive: Boolean?,
        @Field("spoiler_text") spoilerText: String?,
        @Field("visibility") visibility: String?,
        @Field("language") language: String?,
        @Field("scheduled_at") scheduledAt: String?,
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
        return api.postStatus(
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
    }
}

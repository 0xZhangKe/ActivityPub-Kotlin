package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubPollRequestEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubStatusScheduledEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private interface StatusService {

    @FormUrlEncoded
    @POST("/api/v2/media")
    suspend fun postStatus(
        @Field("status") status: String?,
        @Field("media_ids") mediaIds: List<String>?,
        @Field("in_reply_to_id") replyToId: String?,
        @Field("sensitive") isSensitive: Boolean?,
        @Field("spoiler_text") spoilerText: String?,
        @Field("visibility") visibility: String?,
        @Field("language") language: String?,
    ): Result<ActivityPubStatusEntity>

    @FormUrlEncoded
    @POST("/api/v2/media")
    suspend fun postStatusWithPoll(
        @Field("status") status: String?,
        @Field("poll") poll: ActivityPubPollRequestEntity,
        @Field("in_reply_to_id") replyToId: String?,
        @Field("sensitive") isSensitive: Boolean?,
        @Field("spoiler_text") spoilerText: String?,
        @Field("visibility") visibility: String?,
        @Field("language") language: String?,
    ): Result<ActivityPubStatusEntity>

    @FormUrlEncoded
    @POST("/api/v2/media")
    suspend fun postScheduledStatus(
        @Field("status") status: String?,
        @Field("media_ids") mediaIds: List<String>?,
        @Field("in_reply_to_id") replyToId: String?,
        @Field("sensitive") isSensitive: Boolean?,
        @Field("spoiler_text") spoilerText: String?,
        @Field("visibility") visibility: String?,
        @Field("language") language: String?,
    ): Result<ActivityPubStatusScheduledEntity>

    @FormUrlEncoded
    @POST("/api/v2/media")
    suspend fun postScheduledStatusWithPoll(
        @Field("status") status: String?,
        @Field("poll") poll: ActivityPubPollRequestEntity,
        @Field("in_reply_to_id") replyToId: String?,
        @Field("sensitive") isSensitive: Boolean?,
        @Field("spoiler_text") spoilerText: String?,
        @Field("visibility") visibility: String?,
        @Field("language") language: String?,
    ): Result<ActivityPubStatusScheduledEntity>
}

class StatusRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(StatusService::class.java)


}
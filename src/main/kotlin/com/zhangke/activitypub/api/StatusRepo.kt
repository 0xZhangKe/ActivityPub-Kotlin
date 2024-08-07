package com.zhangke.activitypub.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubPollEntity
import com.zhangke.activitypub.entities.ActivityPubPollRequestEntity
import com.zhangke.activitypub.entities.ActivityPubPostStatusRequestEntity
import com.zhangke.activitypub.entities.ActivityPubStatusContextEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubStatusVisibilityEntity
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private interface StatusService {

    @GET("/api/v1/statuses/{id}")
    suspend fun getStatuses(@Path("id") id: String): Result<ActivityPubStatusEntity?>

    @POST("/api/v1/statuses")
    suspend fun postStatus(
        @Body requestBody: ActivityPubPostStatusRequestEntity,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/favourite")
    suspend fun favourite(
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unfavourite")
    suspend fun unfavourite(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/reblog")
    suspend fun reblog(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unreblog")
    suspend fun unreblog(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/bookmark")
    suspend fun bookmark(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unbookmark")
    suspend fun unbookmark(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/pin")
    suspend fun pin(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unpin")
    suspend fun unpin(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @DELETE("/api/v1/statuses/{id}")
    suspend fun delete(@Path("id") id: String): Result<ActivityPubStatusEntity>

    @GET("/api/v1/statuses/{id}/context")
    suspend fun getContext(@Path("id") id: String): Result<ActivityPubStatusContextEntity>

    @POST("/api/v1/polls/{id}/votes")
    suspend fun votes(
        @Path("id") id: String,
        @Body choices: JsonObject
    ): Result<ActivityPubPollEntity>
}

class StatusRepo(client: ActivityPubClient) {

    private val api = client.retrofit.create(StatusService::class.java)

    suspend fun getStatuses(statusId: String): Result<ActivityPubStatusEntity?> {
        return api.getStatuses(id = statusId)
    }

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
        return api.postStatus(requestBody = requestBody)
    }

    suspend fun favourite(id: String): Result<ActivityPubStatusEntity> {
        return api.favourite(id = id)
    }

    suspend fun unfavourite(id: String): Result<ActivityPubStatusEntity> {
        return api.unfavourite(id = id)
    }

    suspend fun reblog(id: String): Result<ActivityPubStatusEntity> {
        return api.reblog(id = id)
    }

    suspend fun unreblog(id: String): Result<ActivityPubStatusEntity> {
        return api.unreblog(id = id)
    }

    suspend fun bookmark(id: String): Result<ActivityPubStatusEntity> {
        return api.bookmark(id = id)
    }

    suspend fun unbookmark(id: String): Result<ActivityPubStatusEntity> {
        return api.unbookmark(id = id)
    }

    suspend fun pin(id: String): Result<ActivityPubStatusEntity> {
        return api.pin(id = id)
    }

    suspend fun unpin(id: String): Result<ActivityPubStatusEntity> {
        return api.unpin(id = id)
    }

    suspend fun delete(id: String): Result<ActivityPubStatusEntity> {
        return api.delete(id = id)
    }

    suspend fun getStatusContext(id: String): Result<ActivityPubStatusContextEntity> {
        return api.getContext(id = id)
    }

    suspend fun votes(
        id: String,
        choices: List<Int>,
    ): Result<ActivityPubPollEntity> {
        val params = JsonObject().apply {
            add("choices", JsonArray().apply {
                choices.forEach {
                    add(it)
                }
            })
        }
        return api.votes(
            id = id,
            choices = params,
        )
    }
}

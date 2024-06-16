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
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

private interface StatusService {

    @POST("/api/v1/statuses")
    suspend fun postStatus(
        @Header("Authorization") authorization: String,
        @Body requestBody: ActivityPubPostStatusRequestEntity,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/favourite")
    suspend fun favourite(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unfavourite")
    suspend fun unfavourite(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/reblog")
    suspend fun reblog(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unreblog")
    suspend fun unreblog(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/bookmark")
    suspend fun bookmark(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @POST("/api/v1/statuses/{id}/unbookmark")
    suspend fun unbookmark(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @DELETE("/api/v1/statuses/{id}")
    suspend fun delete(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusEntity>

    @GET("/api/v1/statuses/{id}/context")
    suspend fun getContext(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubStatusContextEntity>

    @POST("/api/v1/polls/{id}/votes")
    suspend fun votes(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body choices: JsonObject,
    ): Result<ActivityPubPollEntity>
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

    suspend fun favourite(id: String): Result<ActivityPubStatusEntity> {
        return api.favourite(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun unfavourite(id: String): Result<ActivityPubStatusEntity> {
        return api.unfavourite(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun reblog(id: String): Result<ActivityPubStatusEntity> {
        return api.reblog(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun unreblog(id: String): Result<ActivityPubStatusEntity> {
        return api.unreblog(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun bookmark(id: String): Result<ActivityPubStatusEntity> {
        return api.bookmark(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun unbookmark(id: String): Result<ActivityPubStatusEntity> {
        return api.unbookmark(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun delete(id: String): Result<ActivityPubStatusEntity> {
        return api.delete(
            authorization = getAuthorizationHeader(),
            id = id,
        )
    }

    suspend fun getStatusContext(id: String): Result<ActivityPubStatusContextEntity> {
        return api.getContext(
            authorization = getAuthorizationHeader(),
            id = id,
        )
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
            authorization = getAuthorizationHeader(),
            id = id,
            choices = params,
        )
    }
}

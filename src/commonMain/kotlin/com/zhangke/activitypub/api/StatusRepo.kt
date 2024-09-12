package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubEditStatusEntity
import com.zhangke.activitypub.entities.ActivityPubPollEntity
import com.zhangke.activitypub.entities.ActivityPubPollRequestEntity
import com.zhangke.activitypub.entities.ActivityPubPostStatusRequestEntity
import com.zhangke.activitypub.entities.ActivityPubStatusContextEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubStatusVisibilityEntity
import com.zhangke.activitypub.entities.ActivityPubTranslationEntity
import com.zhangke.activitypub.utils.performPagingRequest
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal interface StatusService {

    @GET("api/v1/statuses/{id}")
    suspend fun getStatuses(@Path("id") id: String): ActivityPubStatusEntity?

    @POST("api/v1/statuses")
    suspend fun postStatus(
        @Body requestBody: ActivityPubPostStatusRequestEntity,
    ): ActivityPubStatusEntity

    @PUT("api/v1/statuses/{id}")
    suspend fun editStatus(
        @Path("id") id: String,
        @Body requestBody: ActivityPubEditStatusEntity,
    ): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/favourite")
    suspend fun favourite(
        @Path("id") id: String,
    ): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/unfavourite")
    suspend fun unfavourite(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/reblog")
    suspend fun reblog(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/unreblog")
    suspend fun unreblog(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/bookmark")
    suspend fun bookmark(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/unbookmark")
    suspend fun unbookmark(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/pin")
    suspend fun pin(@Path("id") id: String): ActivityPubStatusEntity

    @POST("api/v1/statuses/{id}/unpin")
    suspend fun unpin(@Path("id") id: String): ActivityPubStatusEntity

    @DELETE("api/v1/statuses/{id}")
    suspend fun delete(@Path("id") id: String): ActivityPubStatusEntity

    @GET("api/v1/statuses/{id}/context")
    suspend fun getContext(@Path("id") id: String): ActivityPubStatusContextEntity

    @POST("api/v1/polls/{id}/votes")
    suspend fun votes(
        @Path("id") id: String,
        @Body choices: JsonElement
    ): ActivityPubPollEntity

    @GET("api/v1/statuses/{id}/reblogged_by")
    fun getReblogBy(
        @Path("id") id: String,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @GET("api/v1/statuses/{id}/favourited_by")
    fun getFavouritesBy(
        @Path("id") id: String,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @FormUrlEncoded
    @POST("api/v1/statuses/{id}/translate")
    suspend fun translate(
        @Path("id") id: String,
        @Field("lang") lang: String,
    ): ActivityPubTranslationEntity
}

class StatusRepo(private val client: ActivityPubClient) {

    private val api = client.ktorfit.createStatusService()

    suspend fun getStatuses(statusId: String): Result<ActivityPubStatusEntity?> {
        return runCatching {
            api.getStatuses(id = statusId)
        }
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
        return runCatching {
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
            api.postStatus(requestBody = requestBody)
        }
    }

    suspend fun editStatus(
        id: String,
        status: String?,
        spoilerText: String?,
        sensitive: Boolean?,
        mediaIds: List<String>? = null,
        mediaAttributes: List<ActivityPubEditStatusEntity.MediaAttributes>? = null,
        poll: ActivityPubPollRequestEntity? = null,
        language: String? = null,
    ): Result<ActivityPubStatusEntity>{
        return runCatching {
            val requestBody = ActivityPubEditStatusEntity(
                status = status,
                spoilerText = spoilerText,
                sensitive = sensitive,
                language = language,
                mediaIds = mediaIds,
                mediaAttributes = mediaAttributes,
                poll = poll,
            )
            api.editStatus(id, requestBody)
        }
    }

    suspend fun favourite(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.favourite(id = id)
        }
    }

    suspend fun unfavourite(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.unfavourite(id = id)
        }
    }

    suspend fun reblog(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.reblog(id = id)
        }
    }

    suspend fun unreblog(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.unreblog(id = id)
        }
    }

    suspend fun bookmark(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.bookmark(id = id)
        }
    }

    suspend fun unbookmark(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.unbookmark(id = id)
        }
    }

    suspend fun pin(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.pin(id = id)
        }
    }

    suspend fun unpin(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.unpin(id = id)
        }
    }

    suspend fun delete(id: String): Result<ActivityPubStatusEntity> {
        return runCatching {
            api.delete(id = id)
        }
    }

    suspend fun getStatusContext(id: String): Result<ActivityPubStatusContextEntity> {
        return runCatching {
            api.getContext(id = id)
        }
    }

    suspend fun votes(
        id: String,
        choices: List<Int>,
    ): Result<ActivityPubPollEntity> {
        return runCatching {
            val params = JsonObject(mapOf(
                "choices" to JsonArray(
                    choices.map { JsonPrimitive(it) },
                ),
            ))
            api.votes(
                id = id,
                choices = params,
            )
        }
    }

    suspend fun getReblogBy(
        statusId: String,
        maxId: String? = null,
        sinceId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getReblogBy(
                        id = statusId,
                        maxId = maxId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                },
            )
        }
    }

    suspend fun getFavouritesBy(
        statusId: String,
        maxId: String? = null,
        sinceId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getFavouritesBy(
                        id = statusId,
                        maxId = maxId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                },
            )
        }
    }

    suspend fun translate(id: String, lan: String): Result<ActivityPubTranslationEntity> {
        return runCatching { api.translate(id = id, lang = lan) }
    }
}

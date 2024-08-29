package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.ActivityPubInstanceApiLevel
import com.zhangke.activitypub.entities.ActivityPubAnnouncementEntity
import com.zhangke.activitypub.entities.ActivityPubInstanceEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import com.zhangke.activitypub.entities.ActivityPubV1InstanceEntity
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface InstanceApi {

    @GET("/api/v2/instance")
    suspend fun v2InstanceInformation(): ActivityPubInstanceEntity

    @GET("/api/v1/instance")
    suspend fun v1InstanceInformation(): ActivityPubV1InstanceEntity

    @GET("/api/v1/announcements")
    suspend fun getAnnouncement(): List<ActivityPubAnnouncementEntity>

    @GET("/api/v1/trends/tags")
    suspend fun getTrendsTags(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<ActivityPubTagEntity>

    @GET("/api/v1/trends/statuses")
    suspend fun getTrendsStatuses(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): List<ActivityPubStatusEntity>
}

class InstanceRepo(private val client: ActivityPubClient) {

    private val api = client.ktorfit.createInstanceApi()

    suspend fun getInstanceInformation(): Result<ActivityPubInstanceEntity> {
        try {
            if (client.apiLevel == ActivityPubInstanceApiLevel.V1) {
                val result = runCatching { api.v1InstanceInformation() }
                if (result.isSuccess) return result.map { it.toInstanceEntity() }
            }
            val v2Result = runCatching { api.v2InstanceInformation() }
            if (v2Result.isSuccess) {
                if (client.apiLevel != ActivityPubInstanceApiLevel.V2) {
                    client.apiLevel = ActivityPubInstanceApiLevel.V2
                }
                return v2Result
            }
            val v1Result = runCatching { api.v1InstanceInformation() }
            if (v1Result.isSuccess) {
                client.apiLevel = ActivityPubInstanceApiLevel.V1
                return v1Result.map { it.toInstanceEntity() }
            }
            return v2Result
        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    /**
     * need login
     */
    suspend fun getAnnouncement(): Result<List<ActivityPubAnnouncementEntity>> {
        return runCatching { api.getAnnouncement() }
    }

    /**
     * 3.0.0 - added
     * 3.5.0 - method signature changed from GET /api/v1/trends to GET /api/v1/trends/tags.
     * The former is a deprecated alias that may be removed in the future.
     */
    suspend fun getTrendsTags(limit: Int, offset: Int): Result<List<ActivityPubTagEntity>> {
        return runCatching { api.getTrendsTags(limit, offset) }
    }

    /**
     * 3.5.0 - added
     */
    suspend fun getTrendsStatuses(limit: Int, offset: Int): Result<List<ActivityPubStatusEntity>> {
        return runCatching { api.getTrendsStatuses(limit, offset) }
    }
}

package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.ActivityPubInstanceApiLevel
import com.zhangke.activitypub.entities.ActivityPubAnnouncementEntity
import com.zhangke.activitypub.entities.ActivityPubInstanceEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import com.zhangke.activitypub.entities.ActivityPubV1InstanceEntity
import retrofit2.http.GET
import retrofit2.http.Query

private interface InstanceApi {

    @GET("/api/v2/instance")
    suspend fun v2InstanceInformation(): Result<ActivityPubInstanceEntity>

    @GET("/api/v1/instance")
    suspend fun v1InstanceInformation(): Result<ActivityPubV1InstanceEntity>

    @GET("/api/v1/announcements")
    suspend fun getAnnouncement(): Result<List<ActivityPubAnnouncementEntity>>

    @GET("/api/v1/trends/tags")
    suspend fun getTrendsTags(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Result<List<ActivityPubTagEntity>>

    @GET("/api/v1/trends/statuses")
    suspend fun getTrendsStatuses(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Result<List<ActivityPubStatusEntity>>
}

class InstanceRepo(private val client: ActivityPubClient) {

    private val api = client.retrofit.create(InstanceApi::class.java)

    suspend fun getInstanceInformation(): Result<ActivityPubInstanceEntity> {
        if (client.apiLevel == ActivityPubInstanceApiLevel.V1) {
            val result = api.v1InstanceInformation()
            if (result.isSuccess) return result.map { it.toInstanceEntity() }
        }
        val v2Result = api.v2InstanceInformation()
        if (v2Result.isSuccess) {
            if (client.apiLevel != ActivityPubInstanceApiLevel.V2) {
                client.apiLevel = ActivityPubInstanceApiLevel.V2
            }
            return v2Result
        }
        val v1Result = api.v1InstanceInformation()
        if (v1Result.isSuccess) {
            client.apiLevel = ActivityPubInstanceApiLevel.V1
            return v1Result.map { it.toInstanceEntity() }
        }
        return v2Result
    }

    /**
     * need login
     */
    suspend fun getAnnouncement(): Result<List<ActivityPubAnnouncementEntity>> {
        return api.getAnnouncement()
    }

    /**
     * 3.0.0 - added
     * 3.5.0 - method signature changed from GET /api/v1/trends to GET /api/v1/trends/tags.
     * The former is a deprecated alias that may be removed in the future.
     */
    suspend fun getTrendsTags(limit: Int, offset: Int): Result<List<ActivityPubTagEntity>> {
        return api.getTrendsTags(limit, offset)
    }

    /**
     * 3.5.0 - added
     */
    suspend fun getTrendsStatuses(limit: Int, offset: Int): Result<List<ActivityPubStatusEntity>> {
        return api.getTrendsStatuses(limit, offset)
    }
}

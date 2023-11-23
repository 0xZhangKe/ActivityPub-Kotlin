package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAnnouncementEntity
import com.zhangke.activitypub.entities.ActivityPubInstanceEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private interface InstanceApi {

    @GET("/api/v2/instance")
    suspend fun instanceInformation(): Result<ActivityPubInstanceEntity>

    @GET("/api/v1/announcements")
    suspend fun getAnnouncement(
        @Header("Authorization") authorization: String,
    ): Result<List<ActivityPubAnnouncementEntity>>

    @GET("/api/v1/trends/tags")
    suspend fun getTrendsTags(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Result<List<ActivityPubTagEntity>>

    @GET("/api/v1/trends/statuses")
    suspend fun getTrendsStatuses(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Result<List<ActivityPubStatusEntity>>
}

class InstanceRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(InstanceApi::class.java)

    suspend fun getInstanceInformation(): Result<ActivityPubInstanceEntity> {
        return api.instanceInformation()
    }

    /**
     * need login
     */
    suspend fun getAnnouncement(): Result<List<ActivityPubAnnouncementEntity>> {
        return api.getAnnouncement(getAuthorizationHeader())
    }

    /**
     * 3.0.0 - added
     * 3.5.0 - method signature changed from GET /api/v1/trends to GET /api/v1/trends/tags.
     * The former is a deprecated alias that may be removed in the future.
     */
    suspend fun getTrendsTags(limit: Int, offset: Int): Result<List<ActivityPubTagEntity>> {
        return api.getTrendsTags(getAuthorizationHeader(), limit, offset)
    }

    /**
     * 3.5.0 - added
     */
    suspend fun getTrendsStatuses(limit: Int, offset: Int): Result<List<ActivityPubStatusEntity>> {
        return api.getTrendsStatuses(limit, offset)
    }
}

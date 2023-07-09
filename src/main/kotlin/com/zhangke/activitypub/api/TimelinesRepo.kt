package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entry.ActivityPubStatusEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by ZhangKe on 2022/12/13.
 */

private interface TimelinesApi {

    @GET("/api/v1/timelines/public?only_media=false")
    suspend fun timelines(
        @Header("Authorization") authorization: String,
        @Query("local") local: Boolean,
        @Query("remote") remote: Boolean,
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int,
    ): Result<List<ActivityPubStatusEntity>>

    @GET("/api/v1/timelines/home")
    suspend fun homeTimeline(
        @Header("Authorization") authorization: String,
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int,
    ): Result<List<ActivityPubStatusEntity>>
}

class TimelinesRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(TimelinesApi::class.java)

    /**
     * 本站timelines
     */
    suspend fun localTimelines(
        limit: Int,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return api.timelines(
            authorization = getAuthorizationHeader(),
            local = true,
            remote = false,
            minId = minId,
            maxId = maxId,
            sinceId = sinceId,
            limit = limit,
        ).collectAuthorizeFailed()
    }

    /**
     * 跨站timelines
     */
    suspend fun publicTimelines(
        limit: Int,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return api.timelines(
            authorization = getAuthorizationHeader(),
            local = false,
            remote = true,
            minId = minId,
            maxId = maxId,
            sinceId = sinceId,
            limit = limit,
        ).collectAuthorizeFailed()
    }

    suspend fun homeTimeline(
        maxId: String? = null,
        minId: String? = null,
        sinceId: String? = null,
        limit: Int,
    ): Result<List<ActivityPubStatusEntity>> {
        return api.homeTimeline(
            authorization = getAuthorizationHeader(),
            maxId = maxId,
            minId = minId,
            sinceId = sinceId,
            limit = limit
        )
    }
}
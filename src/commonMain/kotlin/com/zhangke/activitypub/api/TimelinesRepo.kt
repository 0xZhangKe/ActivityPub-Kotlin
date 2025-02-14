package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

/**
 * Created by ZhangKe on 2022/12/13.
 */

internal interface TimelinesApi {

    @GET("api/v1/timelines/public?only_media=false")
    suspend fun timelines(
        @Query("local") local: Boolean,
        @Query("remote") remote: Boolean,
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int,
    ): List<ActivityPubStatusEntity>

    @GET("api/v1/timelines/home")
    suspend fun homeTimeline(
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int,
    ): List<ActivityPubStatusEntity>

    @GET("api/v1/timelines/list/{list_id}")
    suspend fun getTimelineList(
        @Path("list_id") listId: String,
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int,
    ): List<ActivityPubStatusEntity>

    @GET("api/v1/timelines/tag/{hashtag}")
    suspend fun getTagTimeline(
        @Path("hashtag") hashtag: String,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int,
    ): List<ActivityPubStatusEntity>
}

class TimelinesRepo(client: ActivityPubClient) {

    private val api = client.ktorfit.createTimelinesApi()

    /**
     * 本站timelines
     */
    suspend fun localTimelines(
        limit: Int,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return runCatching {
            api.timelines(
                local = true,
                remote = false,
                minId = minId,
                maxId = maxId,
                sinceId = sinceId,
                limit = limit,
            )
        }
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
        return runCatching {
            api.timelines(
                local = false,
                remote = true,
                minId = minId,
                maxId = maxId,
                sinceId = sinceId,
                limit = limit,
            )
        }
    }

    suspend fun homeTimeline(
        maxId: String? = null,
        minId: String? = null,
        sinceId: String? = null,
        limit: Int,
    ): Result<List<ActivityPubStatusEntity>> {
        return runCatching {
            api.homeTimeline(
                maxId = maxId,
                minId = minId,
                sinceId = sinceId,
                limit = limit,
            )
        }
    }

    suspend fun getTimelineList(
        listId: String,
        limit: Int,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return runCatching {
            api.getTimelineList(
                listId = listId,
                maxId = maxId,
                minId = minId,
                sinceId = sinceId,
                limit = limit,
            )
        }
    }

    suspend fun getTagTimeline(
        hashtag: String,
        limit: Int,
        maxId: String? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return runCatching {
            api.getTagTimeline(
                hashtag = hashtag,
                limit = limit,
                maxId = maxId,
            )
        }
    }
}

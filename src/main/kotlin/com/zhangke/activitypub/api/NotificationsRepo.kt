package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubNotificationsEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private interface NotificationsApi {

    @GET("/api/v1/notifications")
    suspend fun getNotifications(
        @Header("Authorization") authorization: String,
        @Query("max_id") maxId: String?,
        @Query("since_id") sinceId: String?,
        @Query("min_id") minId: String?,
        /**
         * Defaults to 40 notifications. Max 80.
         */
        @Query("limit") limit: Int?,
        /**
         * Return only notifications received from the specified account.
         */
        @Query("account_id") accountId: String?,
        @Query("types") types: List<String>?,
        @Query("exclude_types") excludeTypes: List<String>?,
    ): Result<List<ActivityPubNotificationsEntity>>

}

class NotificationsRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(NotificationsApi::class.java)

    suspend fun getNotifications(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int? = null,
        accountId: String? = null,
        types: List<String>? = null,
        excludeTypes: List<String>? = null,
    ): Result<List<ActivityPubNotificationsEntity>> {
        return api.getNotifications(
            authorization = getAuthorizationHeader(),
            maxId = maxId,
            sinceId = sinceId,
            minId = minId,
            limit = limit,
            accountId = accountId,
            types = types,
            excludeTypes = excludeTypes,
        )
    }
}

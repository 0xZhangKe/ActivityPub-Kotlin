package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubNotificationsEntity
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface NotificationsApi {

    @GET("api/v1/notifications")
    suspend fun getNotifications(
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
        @Query("types[]") types: List<String>?,
        @Query("exclude_types") excludeTypes: List<String>?,
    ): List<ActivityPubNotificationsEntity>

}

class NotificationsRepo(client: ActivityPubClient) {

    private val api = client.ktorfit.createNotificationsApi()

    suspend fun getNotifications(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int? = null,
        accountId: String? = null,
        types: List<String>? = null,
        excludeTypes: List<String>? = null,
    ): Result<List<ActivityPubNotificationsEntity>> {
        return runCatching {
            api.getNotifications(
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
}

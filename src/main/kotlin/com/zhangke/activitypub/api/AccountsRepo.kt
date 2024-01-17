package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubListEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ZhangKe on 2022/12/13.
 */
private interface AccountsApi {

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(@Header("Authorization") authorization: String): Result<ActivityPubAccountEntity>

    @GET("/api/v1/accounts/lookup")
    suspend fun lookup(@Query("acct") acct: String): Result<ActivityPubAccountEntity?>

    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): Result<ActivityPubAccountEntity>

    @GET("/api/v1/accounts/{id}/statuses")
    suspend fun getStatuses(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
        @Query("only_media") onlyMedia: Boolean?,
        @Query("pinned") pinned: Boolean?,
        @Query("exclude_replies") excludeReplies: Boolean?,
        @Query("exclude_reblogs") excludeBlogs: Boolean?,
    ): Result<List<ActivityPubStatusEntity>>

    @GET("/api/v1/lists")
    suspend fun getAccountLists(
        @Header("Authorization") authorization: String,
    ): Result<List<ActivityPubListEntity>>
}

class AccountsRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(AccountsApi::class.java)

    suspend fun verifyCredentials(accessToken: String): Result<ActivityPubAccountEntity> {
        return api.verifyCredentials(buildAuthorizationHeader(accessToken)).collectAuthorizeFailed()
    }

    suspend fun lookup(acct: String): Result<ActivityPubAccountEntity?> {
        return api.lookup(acct).collectAuthorizeFailed()
    }

    suspend fun getAccount(id: String): Result<ActivityPubAccountEntity> {
        return api.getAccount(id).collectAuthorizeFailed()
    }

    suspend fun getStatuses(
        id: String,
        limit: Int? = 20,
        minId: String? = null,
        sinceId: String? = null,
        maxId: String? = null,
        onlyMedia: Boolean? = false,
        pinned: Boolean? = false,
        excludeReplies: Boolean? = false,
        excludeBlogs: Boolean? = false,
    ): Result<List<ActivityPubStatusEntity>> {
        return api.getStatuses(
            authorization = getAuthorizationHeader(),
            id = id,
            limit = limit,
            pinned = pinned,
            maxId = maxId,
            minId = minId,
            sinceId = sinceId,
            onlyMedia = onlyMedia,
            excludeReplies = excludeReplies,
            excludeBlogs = excludeBlogs,
        ).collectAuthorizeFailed()
    }

    suspend fun getAccountLists(): Result<List<ActivityPubListEntity>> {
        return api.getAccountLists(
            authorization = getAuthorizationHeader(),
        )
    }
}

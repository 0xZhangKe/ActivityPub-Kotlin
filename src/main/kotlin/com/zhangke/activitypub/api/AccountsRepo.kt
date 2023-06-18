package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entry.ActivityPubAccount
import com.zhangke.activitypub.entry.ActivityPubStatus
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ZhangKe on 2022/12/13.
 */
private interface AccountsApi {

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(@Header("Authorization") authorization: String): Result<ActivityPubAccount>

    @GET("/api/v1/accounts/lookup")
    suspend fun lookup(@Query("acct") acct: String): Result<ActivityPubAccount>

    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): Result<ActivityPubAccount>

    @GET("/api/v1/accounts/{id}/statuses")
    suspend fun getStatuses(
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
        @Query("only_media") onlyMedia: Boolean?,
        @Query("pinned") pinned: Boolean?,
        @Query("exclude_replies") excludeReplies: Boolean?,
        @Query("exclude_reblogs") excludeBlogs: Boolean?,
        @Query("tagged") tagged: Boolean?,
    ): Result<List<ActivityPubStatus>>
}

class AccountsRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(AccountsApi::class.java)

    suspend fun verifyCredentials(accessToken: String): Result<ActivityPubAccount> {
        return api.verifyCredentials(buildAuthorizationHeader(accessToken)).collectAuthorizeFailed()
    }

    suspend fun lookup(acct: String): Result<ActivityPubAccount> {
        return api.lookup(acct).collectAuthorizeFailed()
    }

    suspend fun getAccount(id: String): Result<ActivityPubAccount> {
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
        tagged: Boolean? = false,
    ): Result<List<ActivityPubStatus>> {
        return api.getStatuses(
            id = id,
            limit = limit,
            pinned = pinned,
            maxId = maxId,
            minId = minId,
            sinceId = sinceId,
            onlyMedia = onlyMedia,
            tagged = tagged,
            excludeReplies = excludeReplies,
            excludeBlogs = excludeBlogs,
        ).collectAuthorizeFailed()
    }
}

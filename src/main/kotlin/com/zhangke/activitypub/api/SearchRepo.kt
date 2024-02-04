package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubSearchEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private interface SearchApi {

    @GET("/api/v2/search")
    suspend fun search(
        @Header("Authorization") authorization: String,
        @Query("q") query: String,
        /**
         * Specify whether to search for only accounts, hashtags, statuses
         */
        @Query("type") type: String?,
        /**
         * Attempt WebFinger lookup? Defaults to false.
         */
        @Query("resolve") resolve: Boolean?,
        /**
         * Only include accounts that the user is following? Defaults to false.
         */
        @Query("following") following: Boolean?,
        /**
         * If provided, will only return statuses authored by this account.
         */
        @Query("account_id") accountId: String?,
        /**
         * Filter out unreviewed tags? Defaults to false. Use true when trying to find trending tags.
         */
        @Query("exclude_unreviewed") excludeUnreviewed: Boolean?,
        @Query("max_id") maxId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
    ): Result<ActivityPubSearchEntity>
}

class SearchRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(SearchApi::class.java)

    suspend fun query(
        query: String,
        resolve: Boolean? = null,
        following: Boolean? = null,
        accountId: String? = null,
        excludeUnreviewed: Boolean? = null,
        maxId: String? = null,
        minId: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<ActivityPubSearchEntity> {
        return api.search(
            authorization = getAuthorizationHeader(),
            type = null,
            query = query,
            resolve = resolve,
            following = following,
            accountId = accountId,
            excludeUnreviewed = excludeUnreviewed,
            maxId = maxId,
            minId = minId,
            limit = limit,
            offset = offset,
        )
    }

    suspend fun queryAccount(
        query: String,
        resolve: Boolean? = null,
        following: Boolean? = null,
        accountId: String? = null,
        excludeUnreviewed: Boolean? = null,
        maxId: String? = null,
        minId: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<List<ActivityPubAccountEntity>> {
        return api.search(
            authorization = getAuthorizationHeader(),
            type = "accounts",
            query = query,
            resolve = resolve,
            following = following,
            accountId = accountId,
            excludeUnreviewed = excludeUnreviewed,
            maxId = maxId,
            minId = minId,
            limit = limit,
            offset = offset,
        ).map { it.accounts }
    }

    suspend fun queryHashtags(
        query: String,
        resolve: Boolean? = null,
        following: Boolean? = null,
        accountId: String? = null,
        excludeUnreviewed: Boolean? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<List<ActivityPubTagEntity>> {
        return api.search(
            authorization = getAuthorizationHeader(),
            type = "hashtags",
            query = query,
            resolve = resolve,
            following = following,
            accountId = accountId,
            excludeUnreviewed = excludeUnreviewed,
            maxId = null,
            minId = null,
            limit = limit,
            offset = offset,
        ).map { it.hashtags }
    }

    suspend fun queryStatus(
        query: String,
        resolve: Boolean? = null,
        following: Boolean? = null,
        accountId: String? = null,
        excludeUnreviewed: Boolean? = null,
        maxId: String? = null,
        minId: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): Result<List<ActivityPubStatusEntity>> {
        return api.search(
            authorization = getAuthorizationHeader(),
            type = "statuses",
            query = query,
            resolve = resolve,
            following = following,
            accountId = accountId,
            excludeUnreviewed = excludeUnreviewed,
            maxId = maxId,
            minId = minId,
            limit = limit,
            offset = offset,
        ).map { it.statuses }
    }
}

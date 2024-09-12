package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubSearchEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface SearchApi {

    @GET("api/v2/search")
    suspend fun search(
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
    ): ActivityPubSearchEntity
}

class SearchRepo(client: ActivityPubClient) {

    private val api = client.ktorfit.createSearchApi()

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
        return runCatching {
            api.search(
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
        return runCatching {
            api.search(
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
            )
        }.map { it.accounts }
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
        return runCatching {
            api.search(
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
            )
        }.map { it.hashtags }
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
        return runCatching {
            api.search(
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
            )
        }.map { it.statuses }
    }
}

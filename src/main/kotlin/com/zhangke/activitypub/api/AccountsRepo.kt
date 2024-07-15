package com.zhangke.activitypub.api

import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubListEntity
import com.zhangke.activitypub.entities.ActivityPubRelationshipEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubSuggestionEntry
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import com.zhangke.activitypub.entities.UpdateFieldRequestEntity
import com.zhangke.activitypub.utils.ActivityPubHeaders
import com.zhangke.activitypub.utils.MediaTypes
import com.zhangke.activitypub.utils.performPagingRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by ZhangKe on 2022/12/13.
 */
private interface AccountsApi {

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(@Header("Authorization") authorization: String?): Result<ActivityPubAccountEntity>

    @GET("/api/v1/accounts/lookup")
    suspend fun lookup(@Query("acct") acct: String): Result<ActivityPubAccountEntity?>

    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): Result<ActivityPubAccountEntity>

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
    ): Result<List<ActivityPubStatusEntity>>

    @GET("/api/v1/lists")
    suspend fun getAccountLists(): Result<List<ActivityPubListEntity>>

    @POST("/api/v1/follow_requests/{account_id}/authorize")
    suspend fun authorizeFollowRequest(
        @Path("account_id") accountId: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/follow_requests/{account_id}/reject")
    suspend fun rejectFollowRequest(
        @Path("account_id") accountId: String,
    ): Result<ActivityPubRelationshipEntity>

    @GET("/api/v1/accounts/relationships")
    suspend fun getRelationships(
        @Query("id[]") ids: List<String>,
        @Query("with_suspended") withSuspended: Boolean,
    ): Result<List<ActivityPubRelationshipEntity>>

    @POST("/api/v1/accounts/{id}/follow")
    suspend fun follow(
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/unfollow")
    suspend fun unfollow(
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/block")
    suspend fun block(
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/unblock")
    suspend fun unblock(
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @GET("/api/v1/domain_blocks")
    suspend fun getDomainBlocks(): Result<List<String>>

    @FormUrlEncoded
    @POST("/api/v1/domain_blocks")
    suspend fun blockDomain(
        @Field("domain") domain: String,
    ): Result<JsonObject>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/v1/domain_blocks", hasBody = true)
    suspend fun unblockDomain(
        @Field("domain") domain: String,
    ): Result<JsonObject>

    @GET("/api/v1/tags/{id}")
    suspend fun getTagInformation(
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

    @POST("/api/v1/tags/{id}/follow")
    suspend fun followTag(
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

    @POST("/api/v1/tags/{id}/unfollow")
    suspend fun unfollowTag(
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

    @PATCH("/api/v1/accounts/update_credentials")
    suspend fun updateCredentials(
        @QueryMap queryMap: Map<String, String>,
    ): Result<ActivityPubAccountEntity>

    @Multipart
    @PATCH("/api/v1/accounts/update_credentials")
    suspend fun updateCredentials(
        @QueryMap queryMap: Map<String, String>,
        @Part name: MultipartBody.Part?,
        @Part note: MultipartBody.Part?,
        @Part avatar: MultipartBody.Part?,
        @Part header: MultipartBody.Part?,
    ): Result<ActivityPubAccountEntity>

    @GET("/api/v2/suggestions")
    suspend fun getSuggestions(
        @Query("limit") limit: Int,
    ): Result<List<ActivityPubSuggestionEntry>>

    @GET("/api/v1/accounts/{id}/followers")
    fun getFollowers(
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Call<List<ActivityPubAccountEntity>>

    @GET("/api/v1/accounts/{id}/following")
    fun getFollowing(
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Call<List<ActivityPubAccountEntity>>

    @FormUrlEncoded
    @POST("/api/v1/accounts/{id}/note")
    suspend fun updateNote(
        @Path("id") id: String,
        @Field("comment") comment: String,
    ): Result<ActivityPubRelationshipEntity>
}

class AccountsRepo(private val client: ActivityPubClient) {

    private val api = client.retrofit.create(AccountsApi::class.java)

    suspend fun verifyCredentials(accessToken: String): Result<ActivityPubAccountEntity> {
        return api.verifyCredentials(ActivityPubHeaders.buildAuthTokenHeader(accessToken))
    }

    suspend fun getCredentialAccount(): Result<ActivityPubAccountEntity> {
        return api.verifyCredentials(null)
    }

    suspend fun lookup(acct: String): Result<ActivityPubAccountEntity?> {
        return api.lookup(acct)
    }

    suspend fun getAccount(id: String): Result<ActivityPubAccountEntity> {
        return api.getAccount(id)
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
            id = id,
            limit = limit,
            pinned = pinned,
            maxId = maxId,
            minId = minId,
            sinceId = sinceId,
            onlyMedia = onlyMedia,
            excludeReplies = excludeReplies,
            excludeBlogs = excludeBlogs,
        )
    }

    suspend fun getAccountLists(): Result<List<ActivityPubListEntity>> {
        return api.getAccountLists()
    }

    suspend fun authorizeFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.authorizeFollowRequest(
            accountId = accountId,
        )
    }

    suspend fun rejectFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.rejectFollowRequest(accountId = accountId)
    }

    suspend fun getRelationships(
        idList: List<String>,
        withSuspended: Boolean = false,
    ): Result<List<ActivityPubRelationshipEntity>> {
        return api.getRelationships(
            ids = idList,
            withSuspended = withSuspended,
        )
    }

    suspend fun follow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.follow(
            id = id,
        )
    }

    suspend fun unfollow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.unfollow(
            id = id,
        )
    }

    suspend fun block(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.block(id = id)
    }

    suspend fun unblock(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.unblock(id = id)
    }

    suspend fun getDomainBlocks(): Result<List<String>> {
        return api.getDomainBlocks()
    }

    suspend fun blockDomain(
        domain: String,
    ): Result<JsonObject> {
        return api.blockDomain(domain = domain)
    }

    suspend fun unblockDomain(
        domain: String,
    ): Result<JsonObject> {
        return api.unblockDomain(domain = domain)
    }

    suspend fun getTagInformation(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.getTagInformation(id = name)
    }

    suspend fun followTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.followTag(id = name)
    }

    suspend fun unfollowTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.unfollowTag(id = name)
    }

    suspend fun updateNote(
        accountId: String,
        note: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.updateNote(accountId, note)
    }

    suspend fun updateCredentials(
        name: String? = null,
        note: String? = null,
        avatarByteArray: ByteArray? = null,
        avatarFileName: String? = null,
        headerByteArray: ByteArray? = null,
        headerFileName: String? = null,
        fieldList: List<UpdateFieldRequestEntity>? = null,
    ): Result<ActivityPubAccountEntity> {
        val namePart = name?.let { MultipartBody.Part.createFormData("display_name", it) }
        val notePart = note?.let { MultipartBody.Part.createFormData("note", it) }
        val avatarPart = avatarByteArray?.toRequestBody(MediaTypes.image)
            ?.let { MultipartBody.Part.createFormData("avatar", avatarFileName, it) }
        val headerPart = headerByteArray?.toRequestBody(MediaTypes.image)
            ?.let { MultipartBody.Part.createFormData("header", headerFileName, it) }
        val queryMap = mutableMapOf<String, String>()
        if (fieldList.isNullOrEmpty().not()) {
            fieldList!!.forEachIndexed { index, entity ->
                queryMap["fields_attributes[$index][name]"] = entity.name
                queryMap["fields_attributes[$index][value]"] = entity.value
            }
        }
        return if (namePart == null &&
            notePart == null &&
            avatarPart == null &&
            headerPart == null
        ) {
            api.updateCredentials(queryMap = queryMap)
        } else {
            api.updateCredentials(
                queryMap = queryMap,
                name = namePart,
                note = notePart,
                avatar = avatarPart,
                header = headerPart,
            )
        }
    }

    /**
     * Maximum number of results to return. Defaults to 40 accounts. Max 80 accounts.
     */
    suspend fun getSuggestions(limit: Int = 80): Result<List<ActivityPubSuggestionEntry>> {
        return api.getSuggestions(limit = limit)
    }

    suspend fun getFollowers(
        id: String,
        limit: Int,
        minId: String? = null,
        sinceId: String? = null,
        maxId: String? = null,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return performPagingRequest(
            gson = client.gson,
            requester = {
                api.getFollowers(
                    id = id,
                    limit = limit,
                    minId = minId,
                    sinceId = sinceId,
                    maxId = maxId,
                )
            }
        )
    }

    suspend fun getFollowing(
        id: String,
        limit: Int,
        minId: String? = null,
        sinceId: String? = null,
        maxId: String? = null,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return performPagingRequest(
            gson = client.gson,
            requester = {
                api.getFollowing(
                    id = id,
                    limit = limit,
                    minId = minId,
                    sinceId = sinceId,
                    maxId = maxId,
                )
            }
        )
    }
}

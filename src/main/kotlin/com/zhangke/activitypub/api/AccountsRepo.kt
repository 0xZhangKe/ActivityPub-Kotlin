package com.zhangke.activitypub.api

import com.google.gson.JsonObject
import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubListEntity
import com.zhangke.activitypub.entities.ActivityPubRelationshipEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import com.zhangke.activitypub.entities.UpdateFieldRequestEntity
import com.zhangke.activitypub.utils.MediaTypes
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    @POST("/api/v1/follow_requests/{account_id}/authorize")
    suspend fun authorizeFollowRequest(
        @Header("Authorization") authorization: String,
        @Path("account_id") accountId: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/follow_requests/{account_id}/reject")
    suspend fun rejectFollowRequest(
        @Header("Authorization") authorization: String,
        @Path("account_id") accountId: String,
    ): Result<ActivityPubRelationshipEntity>

    @GET("/api/v1/accounts/relationships")
    suspend fun getRelationships(
        @Header("Authorization") authorization: String,
        @Query("id[]") ids: List<String>,
        @Query("with_suspended") withSuspended: Boolean,
    ): Result<List<ActivityPubRelationshipEntity>>

    @POST("/api/v1/accounts/{id}/follow")
    suspend fun follow(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/unfollow")
    suspend fun unfollow(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/block")
    suspend fun block(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @POST("/api/v1/accounts/{id}/unblock")
    suspend fun unblock(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubRelationshipEntity>

    @GET("/api/v1/domain_blocks")
    suspend fun getDomainBlocks(
        @Header("Authorization") authorization: String,
    ): Result<List<String>>

    @FormUrlEncoded
    @POST("/api/v1/domain_blocks")
    suspend fun blockDomain(
        @Header("Authorization") authorization: String,
        @Field("domain") domain: String,
    ): Result<JsonObject>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/api/v1/domain_blocks", hasBody = true)
    suspend fun unblockDomain(
        @Header("Authorization") authorization: String,
        @Field("domain") domain: String,
    ): Result<JsonObject>

    @GET("/api/v1/tags/{id}")
    suspend fun getTagInformation(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

    @POST("/api/v1/tags/{id}/follow")
    suspend fun followTag(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

    @POST("/api/v1/tags/{id}/unfollow")
    suspend fun unfollowTag(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
    ): Result<ActivityPubTagEntity>

//    @GET("/api/v1/accounts/verify_credentials")
//    suspend fun verifyCredentials(
//        @Header("Authorization") authorization: String,
//    ): Result<ActivityPubAccountEntity>

    @Multipart
    @PATCH("/api/v1/accounts/update_credentials")
    suspend fun updateCredentials(
        @Header("Authorization") authorization: String,
        @Part name: MultipartBody.Part?,
        @Part note: MultipartBody.Part?,
        @Part avatar: MultipartBody.Part?,
        @Part header: MultipartBody.Part?,
        @Part fieldPart: MultipartBody.Part?,
    ): Result<ActivityPubAccountEntity>
}

class AccountsRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(AccountsApi::class.java)

    suspend fun verifyCredentials(accessToken: String): Result<ActivityPubAccountEntity> {
        return api.verifyCredentials(buildAuthorizationHeader(accessToken)).collectAuthorizeFailed()
    }

    suspend fun getCredentialAccount(): Result<ActivityPubAccountEntity> {
        return api.verifyCredentials(getAuthorizationHeader()).collectAuthorizeFailed()
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

    suspend fun authorizeFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.authorizeFollowRequest(
            authorization = getAuthorizationHeader(),
            accountId = accountId,
        ).collectAuthorizeFailed()
    }

    suspend fun rejectFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.rejectFollowRequest(
            authorization = getAuthorizationHeader(),
            accountId = accountId,
        ).collectAuthorizeFailed()
    }

    suspend fun getRelationships(
        idList: List<String>,
        withSuspended: Boolean = false,
    ): Result<List<ActivityPubRelationshipEntity>> {
        return api.getRelationships(
            authorization = getAuthorizationHeader(),
            ids = idList,
            withSuspended = withSuspended,
        ).collectAuthorizeFailed()
    }

    suspend fun follow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.follow(
            authorization = getAuthorizationHeader(),
            id = id,
        ).collectAuthorizeFailed()
    }

    suspend fun unfollow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.unfollow(
            authorization = getAuthorizationHeader(),
            id = id,
        ).collectAuthorizeFailed()
    }

    suspend fun block(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.block(
            authorization = getAuthorizationHeader(),
            id = id,
        ).collectAuthorizeFailed()
    }

    suspend fun unblock(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return api.unblock(
            authorization = getAuthorizationHeader(),
            id = id,
        ).collectAuthorizeFailed()
    }

    suspend fun getDomainBlocks(): Result<List<String>> {
        return api.getDomainBlocks(
            authorization = getAuthorizationHeader(),
        ).collectAuthorizeFailed()
    }

    suspend fun blockDomain(
        domain: String,
    ): Result<JsonObject> {
        return api.blockDomain(
            authorization = getAuthorizationHeader(),
            domain = domain,
        ).collectAuthorizeFailed()
    }

    suspend fun unblockDomain(
        domain: String,
    ): Result<JsonObject> {
        return api.unblockDomain(
            authorization = getAuthorizationHeader(),
            domain = domain,
        ).collectAuthorizeFailed()
    }

    suspend fun getTagInformation(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.getTagInformation(
            authorization = getAuthorizationHeader(),
            id = name,
        ).collectAuthorizeFailed()
    }

    suspend fun followTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.followTag(
            authorization = getAuthorizationHeader(),
            id = name,
        ).collectAuthorizeFailed()
    }

    suspend fun unfollowTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return api.unfollowTag(
            authorization = getAuthorizationHeader(),
            id = name,
        ).collectAuthorizeFailed()
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
        val fieldPart = fieldList?.let { list ->
            val attributes = JsonObject()
            list.forEachIndexed { index, entity ->
                attributes.add(index.toString(), JsonObject().apply {
                    addProperty("name", entity.name)
                    addProperty("value", entity.value)
                })
            }
//            val requestObject = JsonObject()
//            requestObject.add("fields_attributes", attributes)
            MultipartBody.Part.createFormData("fields_attributes", attributes.toString())
        }
        return api.updateCredentials(
            authorization = getAuthorizationHeader(),
            name = namePart,
            note = notePart,
            avatar = avatarPart,
            header = headerPart,
            fieldPart = fieldPart,
        ).collectAuthorizeFailed()
    }
}

package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubCreateFilterEntity
import com.zhangke.activitypub.entities.ActivityPubFilterEntity
import com.zhangke.activitypub.entities.ActivityPubListEntity
import com.zhangke.activitypub.entities.ActivityPubRelationshipEntity
import com.zhangke.activitypub.entities.ActivityPubStatusEntity
import com.zhangke.activitypub.entities.ActivityPubSuggestionEntry
import com.zhangke.activitypub.entities.ActivityPubTagEntity
import com.zhangke.activitypub.entities.UpdateFieldRequestEntity
import com.zhangke.activitypub.utils.performPagingRequest
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.ReqBuilder
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlinx.serialization.json.JsonElement

/**
 * Created by ZhangKe on 2022/12/13.
 */
internal interface AccountsApi {

    @GET("api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(
        @ReqBuilder ext: HttpRequestBuilder.() -> Unit
    ): ActivityPubAccountEntity

    @GET("api/v1/accounts/lookup")
    suspend fun lookup(
        @Query("acct") acct: String
    ): ActivityPubAccountEntity?

    @GET("api/v1/accounts/search")
    suspend fun search(
        @Query("q") q: String,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("resolve") resolve: Boolean?,
        @Query("following") following: Boolean?,
    ): List<ActivityPubAccountEntity>

    @GET("api/v1/accounts/{id}")
    suspend fun getAccount(
        @Path("id") id: String
    ): ActivityPubAccountEntity

    @GET("api/v1/accounts/{id}/statuses")
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
    ): List<ActivityPubStatusEntity>

    @GET("api/v1/lists")
    suspend fun getAccountLists(): List<ActivityPubListEntity>

    @POST("api/v1/follow_requests/{account_id}/authorize")
    suspend fun authorizeFollowRequest(
        @Path("account_id") accountId: String,
    ): ActivityPubRelationshipEntity

    @POST("api/v1/follow_requests/{account_id}/reject")
    suspend fun rejectFollowRequest(
        @Path("account_id") accountId: String,
    ): ActivityPubRelationshipEntity

    @GET("api/v1/accounts/relationships")
    suspend fun getRelationships(
        @Query("id[]") ids: List<String>,
        @Query("with_suspended") withSuspended: Boolean,
    ): List<ActivityPubRelationshipEntity>

    @POST("api/v1/accounts/{id}/follow")
    suspend fun follow(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @POST("api/v1/accounts/{id}/unfollow")
    suspend fun unfollow(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @POST("api/v1/accounts/{id}/block")
    suspend fun block(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @POST("api/v1/accounts/{id}/unblock")
    suspend fun unblock(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @GET("api/v1/domain_blocks")
    suspend fun getDomainBlocks(): List<String>

    @FormUrlEncoded
    @POST("api/v1/domain_blocks")
    suspend fun blockDomain(
        @Field("domain") domain: String,
    ): JsonElement

    // FIXME: ktorfit support it
    // @FormUrlEncoded
    // @HTTP(method = "DELETE", path = "/api/v1/domain_blocks", hasBody = true)
    // suspend fun unblockDomain(
    //     @Field("domain") domain: String,
    // ): Result<JsonElement>

    @GET("api/v1/tags/{id}")
    suspend fun getTagInformation(
        @Path("id") id: String,
    ): ActivityPubTagEntity

    @POST("api/v1/tags/{id}/follow")
    suspend fun followTag(
        @Path("id") id: String,
    ): ActivityPubTagEntity

    @POST("api/v1/tags/{id}/unfollow")
    suspend fun unfollowTag(
        @Path("id") id: String,
    ): ActivityPubTagEntity

    // @PATCH("/api/v1/accounts/update_credentials")
    // suspend fun updateCredentials(
    //     @QueryMap queryMap: Map<String, String>,
    // ): Result<ActivityPubAccountEntity>

    // FIXME: ktorfit support it
    // @Multipart
    // @PATCH("/api/v1/accounts/update_credentials")
    // suspend fun updateCredentials(
    //     @QueryMap queryMap: Map<String, String>,
    //     @Part name: MultipartBody.Part?,
    //     @Part note: MultipartBody.Part?,
    //     @Part avatar: MultipartBody.Part?,
    //     @Part header: MultipartBody.Part?,
    // ): Result<ActivityPubAccountEntity>

    @GET("api/v2/suggestions")
    suspend fun getSuggestions(
        @Query("limit") limit: Int,
    ): List<ActivityPubSuggestionEntry>

    @GET("api/v1/accounts/{id}/followers")
    fun getFollowers(
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @GET("api/v1/accounts/{id}/following")
    fun getFollowing(
        @Path("id") id: String,
        @Query("min_id") minId: String?,
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @FormUrlEncoded
    @POST("api/v1/accounts/{id}/note")
    suspend fun updateNote(
        @Path("id") id: String,
        @Field("comment") comment: String,
    ): ActivityPubRelationshipEntity

    @GET("api/v1/mutes")
    fun getMutedUserList(
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @POST("api/v1/accounts/{id}/mute")
    suspend fun mute(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @POST("api/v1/accounts/{id}/unmute")
    suspend fun unmute(
        @Path("id") id: String,
    ): ActivityPubRelationshipEntity

    @GET("api/v1/bookmarks")
    fun getBookmarks(
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubStatusEntity>>

    @GET("api/v1/favourites")
    fun getFavourites(
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubStatusEntity>>

    @GET("api/v1/blocks")
    fun getBlockedUserList(
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubAccountEntity>>

    @GET("api/v1/followed_tags")
    fun getFollowedTags(
        @Query("since_id") sinceId: String?,
        @Query("max_id") maxId: String?,
        @Query("min_id") minId: String?,
        @Query("limit") limit: Int?,
    ): Response<List<ActivityPubTagEntity>>

    @GET("api/v2/filters")
    suspend fun getFilters(): List<ActivityPubFilterEntity>

    @GET("api/v2/filters/{id}")
    suspend fun getFilter(@Path("id") id: String): ActivityPubFilterEntity

    @POST("api/v2/filters")
    suspend fun createFilter(
        @Body data: ActivityPubCreateFilterEntity,
    ): ActivityPubFilterEntity

    @PUT("api/v2/filters/{id}")
    suspend fun updateFilter(
        @Path("id") id: String,
        @Body data: ActivityPubCreateFilterEntity,
    ): ActivityPubFilterEntity

    @DELETE("api/v2/filters/{id}")
    suspend fun deleteFilter(@Path("id") id: String): Unit
}

class AccountsRepo(private val client: ActivityPubClient) {

    private val api = client.ktorfit.createAccountsApi()

    suspend fun verifyCredentials(accessToken: String): Result<ActivityPubAccountEntity> {
        return runCatching {
            api.verifyCredentials {
                bearerAuth(accessToken)
            }
        }
    }

    suspend fun getCredentialAccount(): Result<ActivityPubAccountEntity> {
        return runCatching {
            api.verifyCredentials {}
        }
    }

    suspend fun lookup(acct: String): Result<ActivityPubAccountEntity?> {
        return runCatching {
            api.lookup(acct)
        }
    }

    suspend fun search(
        query: String,
        limit: Int = 40,
        offset: Int? = null,
        resolve: Boolean? = null,
        following: Boolean? = null,
    ): Result<List<ActivityPubAccountEntity>> {
        return runCatching {
            api.search(
                q = query,
                limit = limit,
                offset = offset,
                resolve = resolve,
                following = following,
            )
        }
    }

    suspend fun getAccount(id: String): Result<ActivityPubAccountEntity> {
        return runCatching {
            api.getAccount(id)
        }
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
        return runCatching {
            api.getStatuses(
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
    }

    suspend fun getAccountLists(): Result<List<ActivityPubListEntity>> {
        return runCatching {
            api.getAccountLists()
        }
    }

    suspend fun authorizeFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.authorizeFollowRequest(
                accountId = accountId,
            )
        }
    }

    suspend fun rejectFollowRequest(
        accountId: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.rejectFollowRequest(accountId = accountId)
        }
    }

    suspend fun getRelationships(
        idList: List<String>,
        withSuspended: Boolean = false,
    ): Result<List<ActivityPubRelationshipEntity>> {
        return runCatching {
            api.getRelationships(
                ids = idList,
                withSuspended = withSuspended,
            )
        }
    }

    suspend fun follow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.follow(
                id = id,
            )
        }
    }

    suspend fun unfollow(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.unfollow(
                id = id,
            )
        }
    }

    suspend fun block(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.block(id = id)
        }
    }

    suspend fun unblock(
        id: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.unblock(id = id)
        }
    }

    suspend fun getDomainBlocks(): Result<List<String>> {
        return runCatching {
            api.getDomainBlocks()
        }
    }

    suspend fun blockDomain(
        domain: String,
    ): Result<JsonElement> {
        return runCatching {
            api.blockDomain(domain = domain)
        }
    }

    suspend fun unblockDomain(
        domain: String,
    ): Result<JsonElement> {
        return runCatching {
            client.ktorfit.httpClient.delete {
                url {
                    takeFrom(client.ktorfit.baseUrl + "api/v1/domain_blocks")
                }
                headers {
                    contentType(ContentType.Application.FormUrlEncoded)
                }
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("domain", domain)
                        }
                    )
                )
            }.body()
        }
    }

    suspend fun getTagInformation(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return runCatching {
            api.getTagInformation(id = name)
        }
    }

    suspend fun followTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return runCatching {
            api.followTag(id = name)
        }
    }

    suspend fun unfollowTag(
        name: String,
    ): Result<ActivityPubTagEntity> {
        return runCatching {
            api.unfollowTag(id = name)
        }
    }

    suspend fun updateNote(
        accountId: String,
        note: String,
    ): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.updateNote(accountId, note)
        }
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
        return runCatching {
            client.ktorfit.httpClient.patch {
                url {
                    takeFrom(client.ktorfit.baseUrl + "api/v1/accounts/update_credentials")
                    fieldList?.forEachIndexed { index, entity ->
                        parameter("fields_attributes[$index][name]", entity.name)
                        parameter("fields_attributes[$index][value]", entity.value)
                    }
                }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            if (!name.isNullOrEmpty()) {
                                append("display_name", name)
                            }
                            if (!note.isNullOrEmpty()) {
                                append("note", note)
                            }
                            if (avatarFileName != null && avatarByteArray != null) {
                                append("avatar", avatarByteArray, Headers.build {
                                    append(HttpHeaders.ContentType, "image/*")
                                    append(HttpHeaders.ContentDisposition, "filename=\"$avatarFileName\"")
                                })
                            }
                            if (headerFileName != null && headerByteArray != null) {
                                append("header", headerByteArray, Headers.build {
                                    append(HttpHeaders.ContentType, "image/*")
                                    append(HttpHeaders.ContentDisposition, "filename=\"$headerFileName\"")
                                })
                            }
                        }
                    )
                )
            }.body()
        }
    }

    /**
     * Maximum number of results to return. Defaults to 40 accounts. Max 80 accounts.
     */
    suspend fun getSuggestions(limit: Int = 80): Result<List<ActivityPubSuggestionEntry>> {
        return runCatching {
            api.getSuggestions(limit = limit)
        }
    }

    suspend fun getFollowers(
        id: String,
        limit: Int,
        minId: String? = null,
        sinceId: String? = null,
        maxId: String? = null,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
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
    }

    suspend fun getFollowing(
        id: String,
        limit: Int,
        minId: String? = null,
        sinceId: String? = null,
        maxId: String? = null,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
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

    suspend fun getMutedUserList(
        maxId: String? = null,
        sinceId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getMutedUserList(
                        maxId = maxId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                },
            )
        }
    }

    suspend fun getBlockedUserList(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubAccountEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getBlockedUserList(
                        maxId = maxId,
                        minId = minId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                }
            )
        }
    }

    suspend fun getBookmarks(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubStatusEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getBookmarks(
                        maxId = maxId,
                        minId = minId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                }
            )
        }
    }

    suspend fun getFavourites(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubStatusEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getFavourites(
                        maxId = maxId,
                        minId = minId,
                        sinceId = sinceId,
                        limit = limit,
                    )
                }
            )
        }
    }

    suspend fun getFollowedTags(
        maxId: String? = null,
        sinceId: String? = null,
        minId: String? = null,
        limit: Int = 40,
    ): Result<PagingResult<List<ActivityPubTagEntity>>> {
        return runCatching {
            performPagingRequest(
                requester = {
                    api.getFollowedTags(
                        maxId = maxId,
                        sinceId = sinceId,
                        minId = minId,
                        limit = limit,
                    )
                },
            )
        }
    }

    suspend fun mute(id: String): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.mute(id)
        }
    }

    suspend fun unmute(id: String): Result<ActivityPubRelationshipEntity> {
        return runCatching {
            api.unmute(id)
        }
    }

    suspend fun getFilters(): Result<List<ActivityPubFilterEntity>> {
        return runCatching {
            api.getFilters()
        }
    }

    suspend fun getFilter(id: String): Result<ActivityPubFilterEntity> {
        return runCatching {
            api.getFilter(id)
        }
    }

    suspend fun createFilters(
        data: ActivityPubCreateFilterEntity,
    ): Result<ActivityPubFilterEntity> {
        return runCatching {
            api.createFilter(data)
        }
    }

    suspend fun updateFilters(
        id: String,
        data: ActivityPubCreateFilterEntity,
    ): Result<ActivityPubFilterEntity> {
        return runCatching {
            api.updateFilter(id, data)
        }
    }

    suspend fun deleteFilter(id: String): Result<Unit> {
        return runCatching {
            api.deleteFilter(id)
        }
    }
}

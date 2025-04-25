package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubAccountEntity
import com.zhangke.activitypub.entities.ActivityPubListEntity
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.serialization.json.JsonObject

internal interface ListsApi {

    @GET("api/v1/lists")
    suspend fun getAccountLists(): List<ActivityPubListEntity>

    @FormUrlEncoded
    @POST("api/v1/lists")
    suspend fun createList(
        @Field("title") title: String,
        @Field("replies_policy") repliesPolicy: String,
        @Field("exclusive") exclusive: Boolean,
    ): ActivityPubListEntity

    @DELETE("api/v1/lists/{list_id}")
    suspend fun deleteList(
        @Path("list_id") listId: String,
    ): JsonObject

    @GET("api/v1/lists/{list_id}")
    suspend fun getListDetail(
        @Path("list_id") listId: String,
    ): ActivityPubListEntity

    @FormUrlEncoded
    @PUT("api/v1/lists/{list_id}")
    suspend fun updateList(
        @Path("list_id") listId: String,
        @Field("title") title: String,
        @Field("replies_policy") repliesPolicy: String,
        @Field("exclusive") exclusive: Boolean,
    ): ActivityPubListEntity

    @GET("api/v1/lists/{list_id}/accounts")
    suspend fun getAccountsInList(
        @Path("list_id") listId: String,
    ): List<ActivityPubAccountEntity>

    // Add accounts to the given list.
    // Note that the user must be following these accounts.
    @FormUrlEncoded
    @POST("api/v1/lists/{list_id}/accounts")
    suspend fun postAccountsInList(
        @Path("list_id") listId: String,
        @Field("account_ids[]") accountIds: List<String>,
    ): JsonObject

    @DELETE("api/v1/lists/{list_id}/accounts")
    suspend fun deleteAccountsInList(
        @Path("list_id") listId: String,
        @Query("account_ids[]") accountIds: List<String>,
    ): JsonObject

}

class ListsRepo(private val client: ActivityPubClient) {

    private val api = client.ktorfit.createListsApi()

    suspend fun getAccountLists(): Result<List<ActivityPubListEntity>> {
        return runCatching {
            api.getAccountLists()
        }
    }

    suspend fun getListDetail(listId: String): Result<ActivityPubListEntity> {
        return runCatching { api.getListDetail(listId) }
    }

    suspend fun createList(
        title: String,
        repliesPolicy: String,
        exclusive: Boolean,
    ): Result<ActivityPubListEntity> {
        return runCatching { api.createList(title, repliesPolicy, exclusive) }
    }

    suspend fun deleteList(listId: String): Result<JsonObject> {
        return runCatching { api.deleteList(listId) }
    }

    suspend fun getAccountsInList(listId: String): Result<List<ActivityPubAccountEntity>> {
        return runCatching { api.getAccountsInList(listId) }
    }

    suspend fun deleteAccountsInList(listId: String, accounts: List<String>): Result<JsonObject> {
        return runCatching { api.deleteAccountsInList(listId, accounts) }
    }

    suspend fun updateList(
        listId: String,
        title: String,
        repliesPolicy: String,
        exclusive: Boolean,
    ): Result<ActivityPubListEntity> {
        return runCatching { api.updateList(listId, title, repliesPolicy, exclusive) }
    }

    suspend fun postAccountInList(listId: String, accountIds: List<String>): Result<Unit> {
        return runCatching { api.postAccountsInList(listId, accountIds) }
    }
}

package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubTokenEntity
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST

/**
 * Created by ZhangKe on 2022/12/14.
 */

internal interface OAuthApi {

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("scope") scope: String,
    ): ActivityPubTokenEntity

}

class OAuthRepo(client: ActivityPubClient) {

    private val api = client.ktorfit.createOAuthApi()

    fun buildOAuthUrl(
        baseUrl: String,
        clientId: String,
        redirectUri: String,
    ): String {
        return "${baseUrl}/oauth/authorize" +
                "?response_type=code" +
                "&client_id=$clientId" +
                "&redirect_uri=$redirectUri" +
                "&scope=read+write+follow+push"
    }

    suspend fun getToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        scopeList: Array<ActivityPubScope>
    ): Result<ActivityPubTokenEntity> {
        return runCatching {
            api.getToken(
                clientId = clientId,
                clientSecret = clientSecret,
                redirectUri = redirectUri,
                grantType = "authorization_code",
                code = code,
                scope = scopeList.joinToString("+") { it.scope }
            )
        }
    }
}

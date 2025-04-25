package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.SubscribePushRequestEntity
import com.zhangke.activitypub.entities.WebPushSubscriptionEntity
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class PushRepo(private val client: ActivityPubClient) {

    private val subscriptionUrl = "${client.baseUrl}api/v1/push/subscription"

    suspend fun subscribePush(request: SubscribePushRequestEntity): Result<WebPushSubscriptionEntity> {
        return runCatching {
            client.httpClient.post(subscriptionUrl) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
        }
    }

    suspend fun getSubscription(): Result<WebPushSubscriptionEntity> {
        return runCatching {
            client.httpClient.get(subscriptionUrl).body()
        }
    }

    suspend fun updateSubscription(
        mention: Boolean? = null,
        status: Boolean? = null,
        reblog: Boolean? = null,
        follow: Boolean? = null,
        followRequest: Boolean? = null,
        favourite: Boolean? = null,
        poll: Boolean? = null,
        update: Boolean? = null,
        policy: String? = null,
    ): Result<WebPushSubscriptionEntity> {
        return runCatching {
            client.httpClient.put(subscriptionUrl) {
                contentType(ContentType.Application.Json)
                val jsonBody = buildJsonObject {
                    val data = buildJsonObject {
                        val alert = buildJsonObject {
                            putIfNotNull("mention", mention)
                            putIfNotNull("status", status)
                            putIfNotNull("reblog", reblog)
                            putIfNotNull("follow", follow)
                            putIfNotNull("follow_request", followRequest)
                            putIfNotNull("favourite", favourite)
                            putIfNotNull("poll", poll)
                            putIfNotNull("update", update)
                        }
                        put("alert", alert)
                    }
                    put("data", data)
                    if (policy != null) {
                        put("policy", policy)
                    }
                }
                setBody(jsonBody)
            }.body()
        }
    }

    private fun JsonObjectBuilder.putIfNotNull(key: String, value: Boolean?) {
        if (value != null) put(key, value)
    }

    suspend fun removeSubscription(): Result<JsonObject> {
        return runCatching {
            client.httpClient.delete(subscriptionUrl).body()
        }
    }
}

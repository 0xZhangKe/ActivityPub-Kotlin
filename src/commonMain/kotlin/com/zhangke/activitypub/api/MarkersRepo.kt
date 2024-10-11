package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubMarkersEntity
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.takeFrom

class MarkersRepo(private val client: ActivityPubClient) {

    private val apiUrl = "${client.baseUrl}api/v1/markers"

    private companion object {

        const val TIMELINE_HOME = "home"
        const val TIMELINE_NOTIFICATIONS = "notifications"
    }

    suspend fun getMarkers(timeline: List<String>): Result<ActivityPubMarkersEntity> {
        return runCatching {
            client.ktorfit.httpClient.get {
                url(apiUrl)
                url {
                    takeFrom(apiUrl)
                    for (s in timeline) {
                        parameter("timeline[]", s)
                    }
                }
            }.body()
        }
    }

    suspend fun saveMarkers(
        homeLastReadId: String? = null,
        notificationLastReadId: String? = null,
    ): Result<ActivityPubMarkersEntity> {
        return runCatching {
            client.httpClient.submitForm {
                url(apiUrl)
                homeLastReadId?.takeIf { it.isNotBlank() }
                    ?.let { parameter("home[last_read_id]", it) }
                notificationLastReadId?.takeIf { it.isNotBlank() }
                    ?.let { parameter("notifications[last_read_id]", it) }
            }.body()
        }
    }
}

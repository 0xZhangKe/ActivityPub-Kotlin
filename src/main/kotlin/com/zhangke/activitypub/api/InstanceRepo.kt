package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entry.ActivityPubAnnouncementEntity
import com.zhangke.activitypub.entry.ActivityPubInstanceEntity
import retrofit2.http.GET
import retrofit2.http.Header

private interface InstanceApi {

    @GET("/api/v2/instance")
    suspend fun instanceInformation(): Result<ActivityPubInstanceEntity>

    @GET("/api/v1/announcements")
    suspend fun getAnnouncement(
        @Header("Authorization") authorization: String,
    ): Result<List<ActivityPubAnnouncementEntity>>
}

class InstanceRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(InstanceApi::class.java)

    suspend fun getInstanceInformation(): Result<ActivityPubInstanceEntity> {
        return api.instanceInformation()
    }

    /**
     * need login
     */
    suspend fun getAnnouncement(): Result<List<ActivityPubAnnouncementEntity>> {
        return api.getAnnouncement(getAuthorizationHeader())
    }
}
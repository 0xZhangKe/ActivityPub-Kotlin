package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entry.ActivityPubInstanceEntity
import retrofit2.http.GET

private interface InstanceApi {

    @GET("/api/v2/instance")
    suspend fun instanceInformation(): Result<ActivityPubInstanceEntity>
}

class InstanceRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(InstanceApi::class.java)

    suspend fun getInstanceInformation(): Result<ActivityPubInstanceEntity> {
        return api.instanceInformation()
    }
}
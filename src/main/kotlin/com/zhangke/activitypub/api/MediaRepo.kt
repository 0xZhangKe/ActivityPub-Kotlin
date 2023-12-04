package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity
import com.zhangke.activitypub.entities.ActivityPubUpdateMediaRequestEntity
import com.zhangke.activitypub.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

private interface MediaService {

    @Multipart
    @POST("/api/v2/media")
    suspend fun postMedia(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part,
    ): Result<ActivityPubMediaAttachmentEntity>

    @PUT("/api/v1/media/{id}")
    suspend fun updateMedia(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body requestBody: ActivityPubUpdateMediaRequestEntity,
    ): Result<ActivityPubMediaAttachmentEntity>
}

class MediaRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(MediaService::class.java)

    suspend fun postFile(
        fileName: String,
        fileSize: Long,
        byteArray: ByteArray,
        fileMediaType: String,
        onProgress: (Float) -> Unit,
    ): Result<ActivityPubMediaAttachmentEntity> {
        val fileBody = ProgressRequestBody(
            fileSize = fileSize,
            byteArray = byteArray,
            fileMediaType = fileMediaType,
            progressCallback = onProgress,
        )
        val filePart = MultipartBody.Part.createFormData("file", fileName, fileBody)
        return api.postMedia(
            authorization = getAuthorizationHeader(),
            file = filePart,
        ).collectAuthorizeFailed()
    }

    suspend fun updateMedia(
        id: String,
        thumbnail: String? = null,
        description: String? = null,
        focus: String? = null,
    ): Result<ActivityPubMediaAttachmentEntity> {
        return api.updateMedia(
            authorization = getAuthorizationHeader(),
            id = id,
            requestBody = ActivityPubUpdateMediaRequestEntity(
                thumbnail = thumbnail,
                description = description,
                focus = focus,
            ),
        )
    }
}

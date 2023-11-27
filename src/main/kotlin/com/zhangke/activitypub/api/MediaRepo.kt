package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity
import com.zhangke.activitypub.utils.MediaTypes
import com.zhangke.activitypub.utils.ProgressRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.InputStream

private interface MediaService {

    @Multipart
    @POST("/api/v2/media")
    suspend fun postMedia(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody? = null,
    ): Result<ActivityPubMediaAttachmentEntity>

    @Multipart
    @PUT("/api/v1/media/{id}")
    suspend fun updateMedia(
        @Path("id") id: String,
        @Part("thumbnail") thumbnail: String,
        @Part("focus") focus: String,
    ): Result<ActivityPubMediaAttachmentEntity>
}

class MediaRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(MediaService::class.java)

    suspend fun updateMedia(
        id: String,
        thumbnail: String,
        focus: String,
    ): Result<ActivityPubMediaAttachmentEntity> {
        return api.updateMedia(id = id, thumbnail = thumbnail, focus = focus)
    }

    suspend fun postFile(
        fileName: String,
        fileSize: Long,
        inputStream: InputStream,
        fileMediaType: String,
        description: String? = null,
        onProgress: (Float) -> Unit,
    ): Result<ActivityPubMediaAttachmentEntity> {
        val fileBody = ProgressRequestBody(
            fileSize = fileSize,
            inputStream = inputStream,
            fileMediaType = fileMediaType,
            progressCallback = onProgress,
        )
        val filePart = MultipartBody.Part.createFormData("file", fileName, fileBody)
        val descriptionBody = description?.toRequestBody(MediaTypes.text)
        return api.postMedia(file = filePart, description = descriptionBody)
            .collectAuthorizeFailed()
    }
}

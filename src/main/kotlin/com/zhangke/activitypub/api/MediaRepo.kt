package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity
import com.zhangke.activitypub.utils.MediaTypes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

private interface MediaService {

    @Multipart
    @POST("/api/v2/media")
    suspend fun postMedia(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody? = null,
    ): Result<ActivityPubMediaAttachmentEntity>
}

class MediaRepo(client: ActivityPubClient) : ActivityPubBaseRepo(client) {

    private val api = createApi(MediaService::class.java)

    suspend fun postImage(
        filePath: String,
        description: String? = null,
    ): Result<ActivityPubMediaAttachmentEntity> {
        return postFile(filePath, MediaTypes.image, description)
    }

    suspend fun postVideo(
        filePath: String,
        description: String? = null,
    ): Result<ActivityPubMediaAttachmentEntity> {
        return postFile(filePath, MediaTypes.video, description)
    }

    private suspend fun postFile(
        filePath: String,
        fileMediaType: MediaType,
        description: String? = null,
    ): Result<ActivityPubMediaAttachmentEntity> {
        val file = File(filePath)
        val fileBody = file.asRequestBody(fileMediaType)
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileBody)
        val descriptionBody = description?.toRequestBody(MediaTypes.text)
        return api.postMedia(file = filePart, description = descriptionBody)
            .collectAuthorizeFailed()
    }
}

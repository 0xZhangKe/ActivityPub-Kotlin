package com.zhangke.activitypub.api

import com.zhangke.activitypub.ActivityPubClient
import com.zhangke.activitypub.entities.ActivityPubMediaAttachmentEntity
import com.zhangke.activitypub.entities.ActivityPubResponse
import com.zhangke.activitypub.entities.ActivityPubUpdateMediaRequestEntity
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom

internal interface MediaService {

    // @Multipart
    // @POST("/api/v2/media")
    // suspend fun postMedia(
    //     @Part file: MultipartBody.Part,
    // ): ActivityPubMediaAttachmentEntity

    @PUT("api/v1/media/{id}")
    suspend fun updateMedia(
        @Path("id") id: String,
        @Body requestBody: ActivityPubUpdateMediaRequestEntity,
    ): ActivityPubMediaAttachmentEntity
}

class MediaRepo(private val client: ActivityPubClient) {

    private val api = client.ktorfit.createMediaService()

    suspend fun postFile(
        fileName: String,
        fileSize: Long,
        byteArray: ByteArray,
        fileMediaType: String,
        onProgress: (Float) -> Unit,
    ): Result<ActivityPubResponse<ActivityPubMediaAttachmentEntity>> {
        return runCatching {
            val response = client.ktorfit.httpClient.post {
                url { takeFrom(client.ktorfit.baseUrl + "api/v2/media") }
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "file",
                                value = byteArray,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, fileMediaType)
                                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                                },
                            )
                        }
                    )
                )
                onUpload { bytesSentTotal, contentLength ->
                    onProgress(bytesSentTotal.toFloat() / (contentLength ?: fileSize))
                }
            }
            val entity: ActivityPubMediaAttachmentEntity = response.body()
            ActivityPubResponse(
                code = response.status.value,
                response = entity,
            )
        }
    }

    suspend fun updateMedia(
        id: String,
        thumbnail: String? = null,
        description: String? = null,
        focus: String? = null,
    ): Result<ActivityPubMediaAttachmentEntity> {
        return runCatching {
            api.updateMedia(
                id = id,
                requestBody = ActivityPubUpdateMediaRequestEntity(
                    thumbnail = thumbnail,
                    description = description,
                    focus = focus,
                ),
            )
        }
    }

    suspend fun getMedia(id: String): Result<ActivityPubResponse<ActivityPubMediaAttachmentEntity>> {
        return runCatching {
            val response = client.ktorfit.httpClient
                .get(client.ktorfit.baseUrl + "api/v1/media/$id")
            val entity: ActivityPubMediaAttachmentEntity = response.body()
            ActivityPubResponse(
                code = response.status.value,
                response = entity,
            )
        }
    }
}

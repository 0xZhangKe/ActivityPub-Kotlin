package com.zhangke.activitypub.exception

import com.zhangke.activitypub.entities.ActivityPubErrorEntry
import de.jensklingenberg.ktorfit.Response
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.isSuccess
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

/**
 * Created by ZhangKe on 2022/12/3.
 */

@Suppress("MemberVisibilityCanBePrivate")
sealed class ActivityPubHttpException(message: String?, e: Throwable?) :
    RuntimeException(message, e) {

    class ServerInternalException(val errorEntry: ActivityPubErrorEntry?, errorMessage: String?) :
        ActivityPubHttpException(errorEntry?.error ?: errorMessage, null)

    class RequestIllegalException(val errorEntry: ActivityPubErrorEntry?, errorMessage: String?) :
        ActivityPubHttpException(errorEntry?.error ?: errorMessage, null)

    class UnauthorizedException(val errorEntry: ActivityPubErrorEntry?, errorMessage: String?) :
        ActivityPubHttpException(errorEntry?.error ?: errorMessage, null)
}

private val unauthorizedCode = listOf(
    401,
    422
)

@OptIn(ExperimentalSerializationApi::class)
internal suspend fun handleErrorResponseToException(json: Json, response: HttpResponse): Exception {
    val code = response.status.value
    val errorEntry = if (!response.status.isSuccess()) {
        runCatching {
            json.decodeFromStream<ActivityPubErrorEntry>(
                response.bodyAsChannel().toInputStream(),
            )
        }.getOrNull()
    } else null
    val errorMessage = errorEntry?.error
    return when (code) {
        in unauthorizedCode -> ActivityPubHttpException.UnauthorizedException(
            errorEntry,
            errorMessage
        )

        in 400..499 -> ActivityPubHttpException.RequestIllegalException(
            errorEntry,
            errorMessage
        )

        in 500..599 -> ActivityPubHttpException.ServerInternalException(
            errorEntry,
            errorMessage
        )

        else -> RuntimeException(errorMessage, null)
    }
}

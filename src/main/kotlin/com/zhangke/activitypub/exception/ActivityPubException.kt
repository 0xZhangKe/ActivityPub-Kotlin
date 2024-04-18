package com.zhangke.activitypub.exception

import com.google.gson.Gson
import com.zhangke.activitypub.entities.ActivityPubErrorEntry
import retrofit2.Response

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

internal fun handleErrorResponseToException(gson: Gson, response: Response<*>): Exception {
    val code = response.code()
    val errorMessage = response.errorBody()?.string()
    val errorEntry: ActivityPubErrorEntry? = errorMessage?.takeIf { it.isNotEmpty() }
        ?.let {
            try {
                gson.fromJson(it, ActivityPubErrorEntry::class.java)
            } catch (e: Throwable) {
                null
            }
        }
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

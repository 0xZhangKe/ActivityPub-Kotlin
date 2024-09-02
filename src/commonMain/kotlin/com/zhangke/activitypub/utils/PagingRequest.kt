package com.zhangke.activitypub.utils

import com.zhangke.activitypub.api.PagingLinkInfo
import com.zhangke.activitypub.api.PagingResult
import com.zhangke.activitypub.exception.handleErrorResponseToException
import de.jensklingenberg.ktorfit.Response
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal suspend fun <T> performPagingRequest(
    requester: suspend () -> Response<T>,
): PagingResult<T> = withContext(Dispatchers.IO) {
    val response = requester()
    if (response.status.isSuccess()) {
        val pagingInfo = response.headers["link"]?.let {
            PagingUtils.parseNextPageUrl(it)
        } ?: PagingLinkInfo.EMPTY
        PagingResult(
            pagingInfo = pagingInfo,
            data = response.body()!!,
        )
    } else {
        throw handleErrorResponseToException(
            response = response.raw(),
        )
    }
}

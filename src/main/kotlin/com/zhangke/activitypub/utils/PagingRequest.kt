package com.zhangke.activitypub.utils

import com.google.gson.Gson
import com.zhangke.activitypub.api.PagingLinkInfo
import com.zhangke.activitypub.api.PagingResult
import com.zhangke.activitypub.exception.handleErrorResponseToException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

internal suspend fun <T> performPagingRequest(
    gson: Gson,
    requester: suspend () -> Call<T>,
): Result<PagingResult<T>> = withContext(Dispatchers.IO) {
    try {
        val response = requester().execute()
        if (response.isSuccessful && response.body() != null) {
            val pagingInfo = response.headers()["link"]?.let {
                PagingUtils.parseNextPageUrl(it)
            } ?: PagingLinkInfo.EMPTY
            val pagingResult = PagingResult<T>(
                pagingInfo = pagingInfo,
                data = response.body()!!,
            )
            return@withContext Result.success(pagingResult)
        } else {
            return@withContext Result.failure(
                handleErrorResponseToException(
                    gson = gson,
                    response = response,
                )
            )
        }
    } catch (e: Throwable) {
        return@withContext Result.failure(e)
    }
}

package com.zhangke.activitypub.api

data class PagingLinkInfo(
    val preUrl: String?,
    val preSinceId: String?,
    val nextUrl: String?,
    val nextMaxId: String?,
){

    companion object{

        val EMPTY = PagingLinkInfo(null, null, null, null)
    }
}

data class PagingResult<T>(
    val pagingInfo: PagingLinkInfo,
    val data: T,
)

package com.zhangke.activitypub.utils

import com.zhangke.activitypub.api.PagingLinkInfo

internal object PagingUtils {

    private val linkRegex = "(?:,\\s*)?<([^>]+)>|;\\s*(\\w+)=['\"](\\w+)['\"]".toRegex()
    private val maxIdRegex = "max_id=([^&]+)".toRegex()
    private val sinceIdRegex = "max_id=([^&]+)".toRegex()

    fun parseNextPageUrl(link: String): PagingLinkInfo {
        if (link.isEmpty()) {
            return PagingLinkInfo.EMPTY
        }
        var preUrl: String? = null
        var preSinceId: String? = null
        var nextUrl: String? = null
        var nextMaxId: String? = null

        var url: String? = null

        var matchResult = linkRegex.matchAt(link, 0)
        while (matchResult != null) {
            if (url == null) {
                val maybeUrl = matchResult.groupValues.getOrNull(1) ?: continue
                url = maybeUrl
            } else {
                val paramName: String = matchResult.groupValues.getOrNull(2) ?: break
                val paramValue: String = matchResult.groupValues.getOrNull(3) ?: break
                if ("rel" == paramName) {
                    when (paramValue) {
                        "next" -> nextUrl = url
                        "prev" -> preUrl = url
                    }
                    url = null
                }
            }
            matchResult = matchResult.next()
        }
        if (preUrl.isNullOrEmpty().not()) {
            preSinceId = extractSinceId(preUrl!!)
        }
        if (nextUrl.isNullOrEmpty().not()) {
            nextMaxId = extractMaxId(nextUrl!!)
        }
        return PagingLinkInfo(
            preUrl = preUrl,
            preSinceId = preSinceId,
            nextUrl = nextUrl,
            nextMaxId = nextMaxId,
        )
    }

    private fun extractMaxId(url: String): String? {
        return extractPagingId(url, maxIdRegex)
    }

    private fun extractSinceId(url: String): String? {
        return extractPagingId(url, sinceIdRegex)
    }

    private fun extractPagingId(url: String, regex: Regex): String? {
        return regex.find(url)
            ?.value
            ?.split('=')
            ?.takeIf { it.size == 2 }
            ?.last()
    }
}

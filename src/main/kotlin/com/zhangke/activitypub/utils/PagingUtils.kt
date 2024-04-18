package com.zhangke.activitypub.utils

import com.zhangke.activitypub.api.PagingLinkInfo
import java.util.regex.Matcher
import java.util.regex.Pattern

internal object PagingUtils {

    private val linkRegex = Pattern.compile("(?:,\\s*)?<([^>]+)>|;\\s*(\\w+)=['\"](\\w+)['\"]")
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
        val matcher: Matcher = linkRegex.matcher(link)
        var url: String? = null
        while (matcher.find()) {
            if (url == null) {
                val maybeUrl = matcher.group(1) ?: continue
                url = maybeUrl
            } else {
                val paramName: String = matcher.group(2) ?: break
                val paramValue: String = matcher.group(3) ?: break
                if ("rel" == paramName) {
                    when (paramValue) {
                        "next" -> nextUrl = url
                        "prev" -> preUrl = url
                    }
                    url = null
                }
            }
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

package com.zhangke.activitypub.utils

import com.zhangke.activitypub.api.PagingLinkInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class PagingUtilsTest {
    @Test
    fun testParseNextPageUrl() {
        val url = "<https://mastodon.social/api/v1/timelines/home?limit=40&max_id=113042976956718819>; rel=\"next\", <https://mastodon.social/api/v1/timelines/home?limit=40&min_id=113042981499965465>; rel=\"prev\""
        val nextPageUrl = PagingUtils.parseNextPageUrl(url)
        assertEquals(
            PagingLinkInfo(
                preUrl = "https://mastodon.social/api/v1/timelines/home?limit=40&min_id=113042981499965465",
                preSinceId = null,
                nextUrl = "https://mastodon.social/api/v1/timelines/home?limit=40&max_id=113042976956718819",
                nextMaxId = "113042976956718819",
            ),
            nextPageUrl,
        )
    }
}
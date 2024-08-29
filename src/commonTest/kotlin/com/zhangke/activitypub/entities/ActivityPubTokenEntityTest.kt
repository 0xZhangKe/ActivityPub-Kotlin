package com.zhangke.activitypub.entities

import com.zhangke.activitypub.utils.testGlobalJson
import kotlin.test.Test
import kotlin.test.assertEquals

class ActivityPubTokenEntityTest {
    @Test
    fun testJson() {
        val jsonString = "{\"access_token\":\"aa\",\"token_type\":\"Bearer\",\"scope\":\"read write follow push\",\"created_at\":11}"
        val entity: ActivityPubTokenEntity = testGlobalJson.decodeFromString(jsonString)
        assertEquals("aa", entity.accessToken)
        assertEquals("Bearer", entity.tokenType)
        assertEquals("read write follow push", entity.scope)
        assertEquals(11, entity.createdAt)
    }
}
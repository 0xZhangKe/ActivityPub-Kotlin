package com.zhangke.activitypub.entities

import com.zhangke.activitypub.utils.testGlobalJson
import org.junit.Assert.*
import org.junit.Test

class ActivityPubRelationshipEntityTest {
    @Test
    fun testJson() {
        val jsonString = """
            [{"id":"111","following":false,"showing_reblogs":false,"notifying":false,"languages":null,"followed_by":false,"blocking":false,"blocked_by":false,"muting":false,"muting_notifications":false,"requested":false,"requested_by":false,"domain_blocking":false,"endorsed":false,"note":""}]
        """.trimIndent()
        val entities: List<ActivityPubRelationshipEntity> = testGlobalJson.decodeFromString(jsonString)
        assertEquals(1, entities.size)
        assertEquals("111", entities[0].id)
        assertEquals(false, entities[0].following)
        assertEquals(false, entities[0].showingReblogs)
        assertEquals(false, entities[0].notifying)
        assertEquals(null, entities[0].languages)
        assertEquals(false, entities[0].followedBy)
        assertEquals(false, entities[0].blocking)
        assertEquals(false, entities[0].blockedBy)
        assertEquals(false, entities[0].muting)
        assertEquals(false, entities[0].mutingNotifications)
        assertEquals(false, entities[0].requested)
        assertEquals(false, entities[0].requestedBy)
        assertEquals(false, entities[0].domainBlocking)
        assertEquals(false, entities[0].endorsed)
        assertEquals("", entities[0].note)
    }
}
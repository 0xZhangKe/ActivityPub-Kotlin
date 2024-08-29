package com.zhangke.activitypub.entities

import com.zhangke.activitypub.utils.testGlobalJson
import org.junit.Assert.assertEquals
import org.junit.Test

class ActivityPubAccountEntityTest {
    @Test
    fun testJson() {
        val jsonString = """
            {"id":"111","username":"seiko_des","acct":"seiko_des","display_name":"seiko_des","locked":false,"bot":false,"discoverable":null,"indexable":false,"group":false,"created_at":"2021-12-17T00:00:00.000Z","note":"","url":"https://mastodon.social/@seiko_des","uri":"https://mastodon.social/users/seiko_des","avatar":"https://files.mastodon.social/accounts/avatars/107/461/459/233/152/673/original/2bc878e516c91776.jpeg","avatar_static":"https://files.mastodon.social/accounts/avatars/107/461/459/233/152/673/original/2bc878e516c91776.jpeg","header":"https://mastodon.social/headers/original/missing.png","header_static":"https://mastodon.social/headers/original/missing.png","followers_count":0,"following_count":24,"statuses_count":1,"last_status_at":"2022-12-19","hide_collections":null,"noindex":false,"source":{"privacy":"public","sensitive":false,"language":null,"note":"","fields":[],"follow_requests_count":0,"hide_collections":null,"discoverable":null,"indexable":false},"emojis":[],"roles":[],"fields":[],"role":{"id":"-99","name":"","permissions":"65536","color":"","highlighted":false}}
        """.trimIndent()
        val entity: ActivityPubAccountEntity = testGlobalJson.decodeFromString(jsonString)
        assertEquals("111", entity.id)
        assertEquals("seiko_des", entity.username)
        assertEquals("seiko_des", entity.acct)
        assertEquals("seiko_des", entity.displayName)
        assertEquals(false, entity.locked)
        assertEquals(false, entity.bot)
        assertEquals(false, entity.discoverable)
        assertEquals(false, entity.group)
        assertEquals("2021-12-17T00:00:00.000Z", entity.createdAt)
        assertEquals("", entity.note)
        assertEquals("https://mastodon.social/@seiko_des", entity.url)
        assertEquals("https://files.mastodon.social/accounts/avatars/107/461/459/233/152/673/original/2bc878e516c91776.jpeg", entity.avatar)
        assertEquals("https://files.mastodon.social/accounts/avatars/107/461/459/233/152/673/original/2bc878e516c91776.jpeg", entity.avatarStatic)
        assertEquals("https://mastodon.social/headers/original/missing.png", entity.header)
        assertEquals("https://mastodon.social/headers/original/missing.png", entity.headerStatic)
        assertEquals(0, entity.followersCount)
        assertEquals(24, entity.followingCount)
        assertEquals(1, entity.statusesCount)
        assertEquals("2022-12-19", entity.lastStatusAt)
        // assertEquals(null, entity.hideCollections)
        // assertEquals(false, entity.noindex)
        assertEquals("public", entity.source!!.privacy)
        assertEquals(false, entity.source!!.sensitive)
        assertEquals(null, entity.source!!.language)
        assertEquals("", entity.source!!.note)
        // assertEquals(0, entity.source!!.followRequestsCount)
        // assertEquals(null, entity.source!!.hideCollections)
        // assertEquals(null, entity.source!!.discoverable)
        // assertEquals(false, entity.source!!.indexable)
        assertEquals(0, entity.emojis.size)
        // assertEquals(0, entity.roles.size)
        assertEquals(0, entity.fields.size)
        // assertEquals("-99", entity.role!!.id)
        // assertEquals("", entity.role!!.name)
        // assertEquals("65536", entity.role!!.permissions)
        // assertEquals("", entity.role!!.color)
        // assertEquals(false, entity.role!!.highlighted)

    }
}
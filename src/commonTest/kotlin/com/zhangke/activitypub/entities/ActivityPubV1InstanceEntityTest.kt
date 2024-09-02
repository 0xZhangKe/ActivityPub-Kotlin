package com.zhangke.activitypub.entities

import com.zhangke.activitypub.utils.testGlobalJson
import kotlin.test.Test
import kotlin.test.assertEquals

class ActivityPubV1InstanceEntityTest {
    @Test
    fun testJson() {
        val jsonString = """
            {"uri":"mstdn.jp","title":"mstdn.jp","short_description":"Mastodon日本鯖です．\r\nよろしくお願いいたします。\r\n\r\n(Maintained by Sujitech, LLC)","description":"Mastodon日本鯖です．","email":"info@sujitech.com","version":"4.1.18","urls":{"streaming_api":"wss://mstdn.jp"},"stats":{"user_count":407474,"status_count":77712800,"domain_count":67286},"thumbnail":"https://mstdn.jp/packs/media/images/preview-6399aebd96ccf025654e2977454f168f.png","languages":["en"],"registrations":true,"approval_required":false,"invites_enabled":true,"configuration":{"accounts":{"max_featured_tags":10},"statuses":{"max_characters":500,"max_media_attachments":4,"characters_reserved_per_url":23},"media_attachments":{"supported_mime_types":["image/jpeg","image/png","image/gif","image/heic","image/heif","image/webp","image/avif","video/webm","video/mp4","video/quicktime","video/ogg","audio/wave","audio/wav","audio/x-wav","audio/x-pn-wave","audio/vnd.wave","audio/ogg","audio/vorbis","audio/mpeg","audio/mp3","audio/webm","audio/flac","audio/aac","audio/m4a","audio/x-m4a","audio/mp4","audio/3gpp","video/x-ms-asf"],"image_size_limit":10485760,"image_matrix_limit":16777216,"video_size_limit":41943040,"video_frame_rate_limit":60,"video_matrix_limit":2304000},"polls":{"max_options":4,"max_characters_per_option":50,"min_expiration":300,"max_expiration":2629746}},"contact_account":{"id":"261312","username":"mstdn_jp","acct":"mstdn_jp","display_name":"mstdn.jp お知らせ【公式】","locked":false,"bot":false,"discoverable":false,"group":false,"created_at":"2017-06-22T00:00:00.000Z","note":"\u003cp\u003e合同会社分散型ソーシャルネットワーク機構 ( \u003cspan class=\"h-card\"\u003e\u003ca href=\"https://mstdn.jp/@dsno\" class=\"u-url mention\"\u003e@\u003cspan\u003edsno\u003c/span\u003e\u003c/a\u003e\u003c/span\u003e ) が運営しているアカウントです。\u003cbr /\u003emstdn.jpのメンテナンス情報等をトゥートします。\u003c/p\u003e\u003cp\u003e当部署では技術的な対応のみを実施しております。\u003cbr /\u003eお問い合わせ・ご質問等は、 EMAIL: sns@bunsan.social までお願いします。\u003c/p\u003e","url":"https://mstdn.jp/@mstdn_jp","avatar":"https://media.mstdn.jp/accounts/avatars/000/261/312/original/ccc3ba5a66381e6f.png","avatar_static":"https://media.mstdn.jp/accounts/avatars/000/261/312/original/ccc3ba5a66381e6f.png","header":"https://mstdn.jp/headers/original/missing.png","header_static":"https://mstdn.jp/headers/original/missing.png","followers_count":72605,"following_count":6,"statuses_count":136,"last_status_at":"2022-07-19","noindex":false,"emojis":[],"roles":[],"fields":[]},"rules":[]}
        """.trimIndent()
        val entity: ActivityPubV1InstanceEntity = testGlobalJson.decodeFromString(jsonString)
        assertEquals("mstdn.jp", entity.uri)
        assertEquals("mstdn.jp", entity.title)
        assertEquals("Mastodon日本鯖です．", entity.description)
    }
}
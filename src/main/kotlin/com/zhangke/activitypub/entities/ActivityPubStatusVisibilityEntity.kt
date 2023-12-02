package com.zhangke.activitypub.entities

enum class ActivityPubStatusVisibilityEntity(val code: String) {

    PUBLIC("public"),
    UNLISTED("unlisted"),
    PRIVATE("private"),
    DIRECT("direct"),
}

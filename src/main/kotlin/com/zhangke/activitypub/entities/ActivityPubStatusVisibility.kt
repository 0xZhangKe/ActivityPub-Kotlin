package com.zhangke.activitypub.entities

enum class ActivityPubStatusVisibility(val code: String) {

    PUBLIC("public"),
    UNLISTED("unlisted"),
    PRIVATE("private"),
    DIRECT("direct"),
}
